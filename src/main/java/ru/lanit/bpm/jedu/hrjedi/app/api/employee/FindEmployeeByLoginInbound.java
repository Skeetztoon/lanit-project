package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

public interface FindEmployeeByLoginInbound {
    Employee execute(String login);
}
