package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UserJsonDTO;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.CreateMultipleEmployeesInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.RoleRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;
import ru.lanit.bpm.jedu.hrjedi.domain.security.State;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateMultipleEmployeesUseCase implements CreateMultipleEmployeesInbound {
    private final EmployeeRepository employeeRepository;
    private final GenerateSecurePasswordInbound generateSecurePassword;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void execute(List<UserJsonDTO> users) {
        validateRegisteredLogins(users);
        validateRegisteredEmails(users);
        validateRoles(users);

        List<Employee> employees = users.stream()
            .map(this::dtoToEmployee)
            .toList();

        employeeRepository.saveAll(employees);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void validateRegisteredLogins(List<UserJsonDTO> users) {
        Set<String> logins = users.stream()
            .map(user -> user.getUsername().trim().toLowerCase())
            .collect(Collectors.toSet());

        if (employeeRepository.existsByLogins(logins)) {
            throw new EmployeeRegistrationException("Some logins already exist");
        }
    }

    private void validateRegisteredEmails(List<UserJsonDTO> users) {
        Set<String> emails = users.stream()
            .map(UserJsonDTO::getEmail)
            .collect(Collectors.toSet());

        if (employeeRepository.existsByEmails(emails)) {
            throw new EmployeeRegistrationException("Some emails already exist");
        }
    }

    private void validateRoles(List<UserJsonDTO> users) {
        final List<Role> dbRoles = roleRepository.findAll();

        Set<String> userRoleNames = users.stream()
            .flatMap(user -> user.getRoles().stream().map(role -> "ROLE_" + role))
            .collect(Collectors.toSet());

        Set<String> dbRoleNames = dbRoles.stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toSet());

        if (!dbRoleNames.containsAll(userRoleNames)) {
            throw new EmployeeRegistrationException("Could not find some provided roles by role name in the database");
        }
    }

    private Employee dtoToEmployee(UserJsonDTO user) {
        String password = generateSecurePassword.execute();

        Employee employee = new Employee(
            user.getUsername().trim().toLowerCase(),
            user.getFirstName(),
            user.getSecondName(),
            user.getLastName(),
            passwordEncoder.encode(password),
            user.getEmail());

        employee.setRoles(getRoles(new HashSet<>(user.getRoles())));
        employee.setState(State.ACTIVE);

        return employee;
    }

    private Set<Role> getRoles(Set<String> userRoles) {
        Set<Role> roles = new HashSet<>();

        final List<Role> dbRoles = roleRepository.findAll();
        for (String role : userRoles) {
            Role userRole = dbRoles.stream()
                .filter(dbRole -> extractRoleNameFromRoleString(role).equals(dbRole.getName()))
                .findFirst()
                .orElseThrow(() -> new EmployeeRegistrationException("Could not find provided role by role name in the database"));
            roles.add(userRole);
        }
        return roles;
    }

    private RoleName extractRoleNameFromRoleString(String roleString) {
        return RoleName.valueOf("ROLE_" + roleString.trim().toUpperCase());
    }
}
