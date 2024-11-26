package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.adapter.persistence.employee.projection.RoleQuantityProjection;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetRolesQuantityTest {
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_HR = "hr";
    private static final String ROLE_USER = "user";
    private static final long USERS_QUANTITY_ADMIN = 1L;
    private static final long USERS_QUANTITY_HR = 2L;
    private static final long USERS_QUANTITY_USER = 10L;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private GetRolesQuantityUseCase getRolesQuantityUseCase;

    @Test
    public void getRolesQuantity_singleRole() {
        List<RoleQuantityProjection> input = List.of(
            createRoleQuantityProjection(ROLE_ADMIN, USERS_QUANTITY_ADMIN),
            createRoleQuantityProjection(ROLE_HR, USERS_QUANTITY_HR),
            createRoleQuantityProjection(ROLE_USER, USERS_QUANTITY_USER)
        );
        when(employeeRepository.getRolesQuantity()).thenReturn(input);
        Map<String, Long> expectedOutput = Map.of(
            ROLE_ADMIN, USERS_QUANTITY_ADMIN,
            ROLE_HR, USERS_QUANTITY_HR,
            ROLE_USER, USERS_QUANTITY_USER
        );

        Map<String, Long> actualOutput = getRolesQuantityUseCase.execute();

        assertEquals(expectedOutput, actualOutput);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private RoleQuantityProjection createRoleQuantityProjection(String role, long usersQuantity) {
        RoleQuantityProjection projection = Mockito.mock(RoleQuantityProjection.class);
        when(projection.getRole()).thenReturn(role);
        when(projection.getUsersQuantity()).thenReturn(usersQuantity);
        return projection;
    }
}
