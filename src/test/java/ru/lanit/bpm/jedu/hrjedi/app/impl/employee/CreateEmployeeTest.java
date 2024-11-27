package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.InvalidEmailException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.ValidateEmailInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.RoleRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class CreateEmployeeTest {
    private final static String LOGIN = "login";
    private final static String FIRSTNAME = "firstName";
    private final static String PATRONYMIC = "patronymic";
    private final static String LASTNAME = "lastName";
    private final static String PASSWORD = "password";
    private final static String EMAIL = "email";
    private final static Set<String> ROLES = Set.of("USER");
    private final static Role USER = new Role();

    private final static String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ValidateEmailInbound validateEmailInbound;

    @InjectMocks
    private CreateEmployeeUseCase createEmployeeUseCase;

    @Test
    public void success() {
        USER.setName(RoleName.ROLE_USER);

        doNothing().when(validateEmailInbound).execute(EMAIL);
        when(employeeRepository.existsByLogin(LOGIN)).thenReturn(false);
        when(employeeRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(roleRepository.findByName(USER.getName())).thenReturn(Optional.of(USER));
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        assertDoesNotThrow(() -> createEmployeeUseCase.execute(LOGIN, FIRSTNAME, PATRONYMIC, LASTNAME, PASSWORD, EMAIL, ROLES));
        verify(validateEmailInbound).execute(EMAIL);
        verify(employeeRepository).existsByLogin(LOGIN);
        verify(employeeRepository).existsByEmail(EMAIL);
        verify(roleRepository).findByName(USER.getName());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    public void invalidEmailFormat() {
        doThrow(InvalidEmailException.class).when(validateEmailInbound).execute(EMAIL);

        assertThrows(InvalidEmailException.class, () -> createEmployeeUseCase.execute(LOGIN, FIRSTNAME, PATRONYMIC, LASTNAME, PASSWORD, EMAIL, ROLES));
        verifyNoInteractions(employeeRepository, roleRepository);
    }

    @Test
    public void invalidLoginExists() {
        doNothing().when(validateEmailInbound).execute(EMAIL);
        when(employeeRepository.existsByLogin(LOGIN)).thenReturn(true);

        assertThrows(EmployeeRegistrationException.class, () -> createEmployeeUseCase.execute(LOGIN, FIRSTNAME, PATRONYMIC, LASTNAME, PASSWORD, EMAIL, ROLES));
        verify(validateEmailInbound).execute(EMAIL);
        verifyNoInteractions(roleRepository);
    }

    @Test
    public void invalidEmailExists() {
        doNothing().when(validateEmailInbound).execute(EMAIL);
        when(employeeRepository.existsByLogin(LOGIN)).thenReturn(false);
        when(employeeRepository.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(EmployeeRegistrationException.class, () -> createEmployeeUseCase.execute(LOGIN, FIRSTNAME, PATRONYMIC, LASTNAME, PASSWORD, EMAIL, ROLES));
        verify(validateEmailInbound).execute(EMAIL);
        verify(employeeRepository).existsByLogin(LOGIN);
        verifyNoInteractions(roleRepository);
    }

    @Test
    public void invalidRoleNotFound() {
        doNothing().when(validateEmailInbound).execute(EMAIL);
        when(employeeRepository.existsByLogin(LOGIN)).thenReturn(false);
        when(employeeRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(roleRepository.findByName(USER.getName())).thenReturn(Optional.empty());

        assertThrows(EmployeeRegistrationException.class, () -> createEmployeeUseCase.execute(LOGIN, FIRSTNAME, PATRONYMIC, LASTNAME, PASSWORD, EMAIL, ROLES));
        verify(validateEmailInbound).execute(EMAIL);
        verify(employeeRepository).existsByLogin(LOGIN);
    }
}
