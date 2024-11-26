package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UserJsonDTO;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.RoleRepository;
import ru.lanit.bpm.jedu.hrjedi.app.impl.employee.CreateMultipleEmployeesUseCase;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CreateMultipleEmployeesTest {
    private final static Role ADMIN = new Role();
    private final static Role USER = new Role();
    private final static Role HR = new Role();

    private final static String MATVEEV = "matveev";
    private final static String DANILIN = "danilin";

    private static final String GENERATED_PASSWORD = "pass";
    private static final String ENCODED_PASSWORD = "encodedPass";

    private final static UserJsonDTO USER_MATVEEV = UserJsonDTO.builder()
        .email(MATVEEV)
        .username(MATVEEV)
        .roles(List.of("HR"))
        .build();
    private final static UserJsonDTO USER_DANILIN = UserJsonDTO.builder()
        .email(DANILIN)
        .username(DANILIN)
        .roles(List.of("USER"))
        .build();

    private static final List<UserJsonDTO> USERS_LIST = List.of(USER_MATVEEV, USER_DANILIN);
    private static final Set<String> EMAILS_LOGINS = Set.of(MATVEEV, DANILIN);

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private GenerateSecurePasswordInbound generateSecurePassword;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateMultipleEmployeesUseCase createMultipleEmployeesUseCase;

    @Test
    public void invalidByEmail() {
        when(employeeRepository.existsByEmails(EMAILS_LOGINS)).thenReturn(true);

        assertThrows(
            EmployeeRegistrationException.class,
            () -> createMultipleEmployeesUseCase.execute(USERS_LIST)
        );

        verify(employeeRepository, times(1)).existsByEmails(EMAILS_LOGINS);
    }

    @Test
    public void invalidByLogin() {
        when(employeeRepository.existsByLogins(EMAILS_LOGINS)).thenReturn(true);

        assertThrows(
            EmployeeRegistrationException.class,
            () -> createMultipleEmployeesUseCase.execute(USERS_LIST)
        );

        verify(employeeRepository, times(1)).existsByLogins(EMAILS_LOGINS);
    }

    @Test
    public void invalidByRoles() {
        ADMIN.setName(RoleName.ROLE_ADMIN);
        USER.setName(RoleName.ROLE_USER);

        when(roleRepository.findAll()).thenReturn(List.of(ADMIN, USER));

        assertThrows(
            EmployeeRegistrationException.class,
            () -> createMultipleEmployeesUseCase.execute(USERS_LIST)
        );
    }

    @Test
    public void success() {
        HR.setName(RoleName.ROLE_HR);

        when(employeeRepository.existsByLogins(Set.of(MATVEEV))).thenReturn(false);
        when(employeeRepository.existsByLogins(Set.of(MATVEEV))).thenReturn(false);
        when(roleRepository.findAll()).thenReturn(List.of(HR));
        when(generateSecurePassword.execute()).thenReturn(GENERATED_PASSWORD);
        when(passwordEncoder.encode(GENERATED_PASSWORD)).thenReturn(ENCODED_PASSWORD);

        Assertions.assertDoesNotThrow(() -> createMultipleEmployeesUseCase.execute(List.of(USER_MATVEEV)));
    }
}
