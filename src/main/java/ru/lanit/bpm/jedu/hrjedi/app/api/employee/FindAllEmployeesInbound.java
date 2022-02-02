package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import java.util.List;

public interface FindAllEmployeesInbound {
    List<Employee> execute();
}
