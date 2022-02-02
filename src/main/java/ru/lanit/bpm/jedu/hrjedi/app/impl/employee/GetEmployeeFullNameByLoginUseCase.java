package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetEmployeeFullNameByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetEmployeeFullNameInbound;

@Component
@RequiredArgsConstructor
public class GetEmployeeFullNameByLoginUseCase implements GetEmployeeFullNameByLoginInbound {
    private final GetEmployeeFullNameInbound getEmployeeFullNameInbound;
    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;

    @Transactional(readOnly = true)
    @Override
    public String execute(String login) {
        return getEmployeeFullNameInbound.execute(findEmployeeByLoginInbound.execute(login));
    }
}
