package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.InvalidEmailException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.ValidateEmailInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class UpdateEmployeeEmailTest {
    private final static String LOGIN = "someLogin";
    private final static String VALID_EMAIL = "someEmail@mail.com";
    private final static String INVALID_EMAIL = "someWrongEmail@@mail.com";

    @Mock
    private FindEmployeeByLoginInbound findEmployeeByLoginInbound;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ValidateEmailInbound validateEmailInbound;

    @InjectMocks
    private UpdateEmployeeEmailUseCase updateEmployeeEmailUseCase;

    @Test
    public void success() {
        Employee employee = new Employee();

        doNothing().when(validateEmailInbound).execute(VALID_EMAIL);
        when(findEmployeeByLoginInbound.execute(LOGIN)).thenReturn(employee);

        assertDoesNotThrow(() -> updateEmployeeEmailUseCase.execute(LOGIN, VALID_EMAIL));
        verify(validateEmailInbound).execute(VALID_EMAIL);
        verify(findEmployeeByLoginInbound).execute(LOGIN);
        verify(employeeRepository).save(employee);
    }

    @Test
    public void invalidEmail() {
        doThrow(InvalidEmailException.class).when(validateEmailInbound).execute(INVALID_EMAIL);

        assertThrows(InvalidEmailException.class, () -> updateEmployeeEmailUseCase.execute(LOGIN, INVALID_EMAIL));
        verifyNoInteractions(findEmployeeByLoginInbound, employeeRepository);
    }

    @Test
    public void noUserFound() {
        doNothing().when(validateEmailInbound).execute(VALID_EMAIL);
        when(findEmployeeByLoginInbound.execute(LOGIN)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> updateEmployeeEmailUseCase.execute(LOGIN, VALID_EMAIL));
        verify(validateEmailInbound).execute(VALID_EMAIL);
        verify(findEmployeeByLoginInbound).execute(LOGIN);
        verifyNoInteractions(employeeRepository);
    }
}
