package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class FindEmployeeByLoginUseCase implements FindEmployeeByLoginInbound {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Employee execute(String login) {
        return employeeRepository.findByLoginIgnoreCase(login.trim())
            .orElseThrow(() -> new EntityNotFoundException("User Not Found with -> username: " + login));
    }
}
