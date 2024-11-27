package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UserJsonDTO;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UsersJsonWrapper;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.CreateMultipleEmployeesInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class LoadUsersFromJsonTest {
    private final static String HR = "HR";
    private final static UserJsonDTO MATVEEV = UserJsonDTO.builder()
        .username("matveev")
        .firstName("matvey")
        .secondName("matveevich")
        .lastName("matveev")
        .email("matveev")
        .roles(List.of(HR))
        .build();

    @Mock
    private CreateMultipleEmployeesInbound createMultipleEmployeesInbound;

    @InjectMocks
    private LoadUsersFromJsonUseCase loadUsersFromJsonUseCase;

    @Test
    public void success() {
        UsersJsonWrapper usersWrapper = new UsersJsonWrapper();
        usersWrapper.setUsers(List.of(MATVEEV));

        doNothing().when(createMultipleEmployeesInbound).execute(anyList());

        boolean result = loadUsersFromJsonUseCase.execute(usersWrapper);

        assertTrue(result);
        verify(createMultipleEmployeesInbound, times(1)).execute(anyList());
    }

    @Test
    public void emptyUsersList() {
        UsersJsonWrapper usersWrapper = new UsersJsonWrapper();
        usersWrapper.setUsers(List.of());

        boolean result = loadUsersFromJsonUseCase.execute(usersWrapper);

        assertFalse(result);
        verify(createMultipleEmployeesInbound, never()).execute(anyList());
    }

    @Test
    public void failureCreatingEmployees() {
        UsersJsonWrapper usersWrapper = new UsersJsonWrapper();
        usersWrapper.setUsers(List.of(MATVEEV));

        doThrow(new EmployeeRegistrationException("Some error"))
            .when(createMultipleEmployeesInbound).execute(anyList());

        boolean result = loadUsersFromJsonUseCase.execute(usersWrapper);

        assertFalse(result);
        verify(createMultipleEmployeesInbound, times(1)).execute(anyList());
    }
}
