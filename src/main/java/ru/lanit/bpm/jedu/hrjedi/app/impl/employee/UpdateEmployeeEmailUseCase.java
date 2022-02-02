package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.UpdateEmployeeEmailInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

@Component
@RequiredArgsConstructor
public class UpdateEmployeeEmailUseCase implements UpdateEmployeeEmailInbound {
    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;
    private final EmployeeRepository employeeRepository;

    @Transactional
    @Override
    public void execute(String login, String email) {
        Employee employee = findEmployeeByLoginInbound.execute(login);
        employee.setEmail(email);
        employeeRepository.save(employee);
    }
}
