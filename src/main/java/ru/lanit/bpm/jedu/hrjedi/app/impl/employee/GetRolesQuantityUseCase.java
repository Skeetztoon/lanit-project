package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetRolesQuantityInbound;

import ru.lanit.bpm.jedu.hrjedi.adapter.persistence.employee.projection.RoleQuantityProjection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetRolesQuantityUseCase implements GetRolesQuantityInbound {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Map<String, Long> execute() {
        return employeeRepository.getRolesQuantity()
            .stream()
            .collect(
                Collectors.toMap(
                    RoleQuantityProjection::getRole,
                    RoleQuantityProjection::getUsersQuantity
                )
            );
    }
}
