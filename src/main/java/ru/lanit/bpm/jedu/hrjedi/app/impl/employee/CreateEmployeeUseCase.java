package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.CreateEmployeeInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.ValidateEmailInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.RoleRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;
import ru.lanit.bpm.jedu.hrjedi.domain.security.State;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateEmployeeUseCase implements CreateEmployeeInbound {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidateEmailInbound validateEmail;

    @Transactional
    @Override
    public void execute(String login, String firstName, String patronymic, String lastName, String password, String email, Set<String> rolesStrings) {
        String trimmedLoginInLowerCase = login.trim().toLowerCase();

        validateEmail.execute(email);
        validateRegisteredLogin(trimmedLoginInLowerCase);
        validateRegisteredEmail(email);

        Employee user = new Employee(trimmedLoginInLowerCase, firstName, patronymic, lastName, passwordEncoder.encode(password), email);
        user.setRoles(validateAndGetRegisteredRoles(rolesStrings));
        user.setState(State.ACTIVE);
        employeeRepository.save(user);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void validateRegisteredLogin(String login) {
        if (employeeRepository.existsByLogin(login)) {
            throw new EmployeeRegistrationException("Employee with this login already exists!");
        }
    }

    private void validateRegisteredEmail(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new EmployeeRegistrationException("Employee with this email already exists!");
        }
    }

    private Set<Role> validateAndGetRegisteredRoles(Set<String> rolesStrings) {
        Set<Role> registeredRoles = new HashSet<>();

        rolesStrings.forEach(roleString -> {
            RoleName registeredRoleName = extractRoleNameFromRoleString(roleString);
            Role registeredRole = roleRepository.findByName(registeredRoleName)
                .orElseThrow(() -> new EmployeeRegistrationException("Could not find provided role by role name in the database"));
            registeredRoles.add(registeredRole);
        });

        return registeredRoles;
    }

    private RoleName extractRoleNameFromRoleString(String roleString) {
        return RoleName.valueOf("ROLE_" + roleString.trim().toUpperCase());
    }
}
