package ru.lanit.bpm.jedu.hrjedi.adapter.accounting;

import com.ibm.mq.spring.boot.MQAutoConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.CreateVacationDocumentsException;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = {MQAutoConfiguration.class, JmsAutoConfiguration.class, CreateVacationDocumentsAdapter.class},
    properties = {"ru.lanit.bpm.jedu.hrjedi.queues.2t-cv-request=YURLOV.IN",
        "ru.lanit.bpm.jedu.hrjedi.queues.2t-cv-response=YURLOV.OUT"}
)
public class CreateVacationDocumentsAdapterIntegrationTest {
    private static final Employee EMPLOYEE_IVANOV = new Employee("ivanov", "", "", "", "", "");
    private static final Employee EMPLOYEE_PETROV = new Employee("petrov", "", "", "", "", "");
    private static final Employee EMPLOYEE_SERGEEV = new Employee("sergeev", "", "", "", "", "");

    private static final LocalDate DATE_START = LocalDate.now();
    private static final LocalDate DATE_END = LocalDate.now().plusDays(7);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private CreateVacationDocumentsAdapter createVacationDocumentsAdapter;

    @Test
    public void success() {
        Vacation vacation = new Vacation(EMPLOYEE_IVANOV, DATE_START, DATE_END);

        assertDoesNotThrow(() -> createVacationDocumentsAdapter.execute(vacation));
    }

    @Test
    public void employeeNotFound() throws Exception {
        Vacation vacation = new Vacation(EMPLOYEE_PETROV, DATE_START, DATE_END);
        expectedException.expect(CreateVacationDocumentsException.class);
        expectedException.expectMessage(containsStringIgnoringCase("Не найден сотрудник с идентификатором 'petrov'"));

        createVacationDocumentsAdapter.execute(vacation);
    }

    @Test
    public void responseTimeout() throws Exception {
        Vacation vacation = new Vacation(EMPLOYEE_SERGEEV, DATE_START, DATE_END);
        expectedException.expect(CreateVacationDocumentsException.class);
        expectedException.expectMessage(containsStringIgnoringCase("Не получен ответ"));

        createVacationDocumentsAdapter.execute(vacation);
    }
}
