package ru.lanit.bpm.jedu.hrjedi.adapter.accounting;

import com.ibm.mq.spring.boot.MQAutoConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsStringIgnoringCase;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MQAutoConfiguration.class, JmsAutoConfiguration.class})
public class AccountingControllerIntegrationTest {
    private static final Employee EMPLOYEE_IVANOV = new Employee("ivanov", "", "", "", "", "");
    private static final Employee EMPLOYEE_PETROV = new Employee("petrov", "", "", "", "", "");
    private static final Employee EMPLOYEE_SERGEEV = new Employee("sergeev", "", "", "", "", "");

    private static final LocalDate DATE_START = LocalDate.now();
    private static final LocalDate DATE_END = LocalDate.now().plus(7, ChronoUnit.DAYS);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private AccountingController accountingController;

    @Before
    public void setUp() {
        accountingController = new AccountingController();
    }

    @Test
    public void success() throws Exception {
        Vacation vacation = new Vacation(EMPLOYEE_IVANOV, DATE_START, DATE_END);
        Vacation vac = new Vacation();

        accountingController.createVacationDocuments(vacation);
    }

    @Test
    public void employeeNotFound() throws Exception {
        Vacation vacation = new Vacation(EMPLOYEE_PETROV, DATE_START, DATE_END);
        expectedException.expect(AccountingException.class);
        expectedException.expectMessage(containsStringIgnoringCase("Не найден сотрудник с идентификатором"));

        accountingController.createVacationDocuments(vacation);
    }

    @Test
    public void responseTimeout() throws Exception {
        Vacation vacation = new Vacation(EMPLOYEE_SERGEEV, DATE_START, DATE_END);
        expectedException.expect(AccountingException.class);
        expectedException.expectMessage(containsStringIgnoringCase("Не получен ответ"));

        accountingController.createVacationDocuments(vacation);
    }
}
