package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindAllEmployeesInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllEmployeesUseCase implements FindAllEmployeesInbound {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Employee> execute() {
        return employeeRepository.findAll();
    }
}
