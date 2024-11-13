package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetRolesQuantityInbound;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetRolesQuantityUseCase implements GetRolesQuantityInbound {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Map<String, Long> execute() {
        List<Map<String, Object>> rawData = employeeRepository.getRolesQuantity();

        return rawData.stream().collect(
            Collectors.toMap(
                entry -> (String) entry.get("ROLE"),
                entry -> ((BigInteger) entry.get("USERS_QUANTITY")).longValue()
            )
        );
    }
}
