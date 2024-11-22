package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetUsersWithRolesInbound;

import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.UserWithRolesProjection;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.dto.RoleDto;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.dto.UserWithRolesDto;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetUsersWithRolesUseCase implements GetUsersWithRolesInbound {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<UserWithRolesDto> execute() {
        return transformData(employeeRepository.getUsersWithRoles());
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private List<UserWithRolesDto> transformData(List<UserWithRolesProjection> input) {
        return input.stream().map(user -> new UserWithRolesDto(
            user.getUserCredentials(),
            Arrays.stream(user.getRoles().toArray())
                .map(role -> new RoleDto(role.toString()))
                .toList()
        )).toList();
    }
}
