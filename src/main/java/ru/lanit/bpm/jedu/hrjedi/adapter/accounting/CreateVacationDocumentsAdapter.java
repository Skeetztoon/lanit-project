package ru.lanit.bpm.jedu.hrjedi.adapter.accounting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public JmsTemplate jmsTemplate;

    public void execute(Vacation vacation) throws CreateVacationDocumentsException {
        Employee employee = vacation.getEmployee();

        LOGGER.info("Attempting to send request on {} vacation", employee.getLogin());

        String login = employee.getLogin();
        String startDate = vacation.getStart().format(DAY_FORMATTER);
        String endDate = vacation.getEnd().format(DAY_FORMATTER);

        try {
            CreateVacationDocumentsRq request = new CreateVacationDocumentsRq();
            request.setLogin(login);
            request.setStartDate(startDate);
            request.setEndDate(endDate);
            sendRequest(request);

            CreateVacationDocumentsRs response = receiveResponse();
            if (!response.getStatus().equals("OK")) {
                throw new CreateVacationDocumentsException("Не найден сотрудник с идентификатором");
            }
        } catch (CreateVacationDocumentsException e) {
            throw e;
        } catch (Exception e) {
            throw new CreateVacationDocumentsException("Не получен ответ");
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
        String req = marshalToXml(request);
        jmsTemplate.convertAndSend("YURLOV.IN", req);
    }

    private CreateVacationDocumentsRs receiveResponse() throws JAXBException {
        String xmlMessage = (String) jmsTemplate.receiveAndConvert("YURLOV.OUT");
        return unmarshalFromXml(xmlMessage);
    }
}
