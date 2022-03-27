package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

@Component
public class GetEmployeeFullName {
    public String execute(Employee employee) {
        return employee.getPatronymic() == null ? String.format("%s %s", employee.getFirstName(), employee.getLastName()) :
            String.format("%s %s %s", employee.getFirstName(), employee.getPatronymic(), employee.getLastName());
    }
}
