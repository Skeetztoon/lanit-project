package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

@Component
@RequiredArgsConstructor
public class FindHeadOfHrEmployee {
    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;

    @Value("${ru.lanit.bpm.jedu.hrjedi.headOfHrLogin}")
    private String headOfHrLogin;

    @Transactional(readOnly = true)
    public Employee execute() {
        return findEmployeeByLoginInbound.execute(headOfHrLogin);
    }
}
