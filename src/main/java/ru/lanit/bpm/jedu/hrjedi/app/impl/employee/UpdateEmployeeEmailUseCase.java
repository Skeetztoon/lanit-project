package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.InvalidEmailException;
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
        final String emailPattern = "^[a-zA-Z0-9А-Яа-я.-]+@[a-zA-Z0-9А-Яа-я.-]+$";
        if (email.matches(emailPattern)) {
            Employee employee = findEmployeeByLoginInbound.execute(login);
            employee.setEmail(email);
            employeeRepository.save(employee);
        } else {
            throw new InvalidEmailException("Provided email address is not valid");
        }
    }
}
