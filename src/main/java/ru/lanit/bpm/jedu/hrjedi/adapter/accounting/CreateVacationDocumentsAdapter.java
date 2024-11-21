package ru.lanit.bpm.jedu.hrjedi.adapter.accounting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.CreateVacationDocumentsException;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.CreateVacationDocumentsOutbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.CreateVacationDocumentsRq;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.CreateVacationDocumentsRs;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;

@Controller
public class CreateVacationDocumentsAdapter implements CreateVacationDocumentsOutbound {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateVacationDocumentsAdapter.class);
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Value("${ru.lanit.bpm.jedu.hrjedi.queues.2t-cv-request}")
    private String inQueue;

    @Value("${ru.lanit.bpm.jedu.hrjedi.queues.2t-cv-response}")
    private String outQueue;

    @Autowired
    public JmsTemplate jmsTemplate;

    public void execute(Vacation vacation) throws JAXBException, CreateVacationDocumentsException {
        Employee employee = vacation.getEmployee();
        String login = employee.getLogin();
        LOGGER.info("Attempting to send request on {} vacation", login);

        CreateVacationDocumentsRq request = CreateVacationDocumentsRq.builder()
            .login(login)
            .startDate(vacation.getStart().format(DAY_FORMATTER))
            .endDate(vacation.getEnd().format(DAY_FORMATTER))
            .build();
        sendRequest(request);

        CreateVacationDocumentsRs response = receiveResponse();
        if (!response.getStatus().equals("OK")) {
            throw new CreateVacationDocumentsException(response.getDescription());
        }

        LOGGER.info("Request processed successfully for {}", login);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private String marshalToXml(CreateVacationDocumentsRq request) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CreateVacationDocumentsRq.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        StringWriter requestXml = new StringWriter();
        marshaller.marshal(request, requestXml);
        return requestXml.toString();
    }

    private CreateVacationDocumentsRs unmarshalFromXml(String response) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CreateVacationDocumentsRs.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader responseXml = new StringReader(response);
        return (CreateVacationDocumentsRs) unmarshaller.unmarshal(responseXml);
    }

    private void sendRequest(CreateVacationDocumentsRq request) throws JAXBException {
        String requestXml = marshalToXml(request);
        jmsTemplate.convertAndSend(inQueue, requestXml);
    }

    private CreateVacationDocumentsRs receiveResponse() throws CreateVacationDocumentsException, JAXBException {
        String responseXml = (String) jmsTemplate.receiveAndConvert(outQueue);
        if (responseXml == null) {
            throw new CreateVacationDocumentsException("Не получен ответ");
        }
        return unmarshalFromXml(responseXml);
    }
}
