package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.CreateEmployeeInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;

@RunWith(MockitoJUnitRunner.class)
public class LoadUsersFromJsonTest {
    @Mock
    private GenerateSecurePasswordInbound generateSecurePassword;

    @Mock
    private CreateEmployeeInbound createEmployee;

    @InjectMocks
    private LoadUsersFromJsonUseCase loadUsersFromJson;

    @Test
    public void validNoPatronymic() {

    }
}
