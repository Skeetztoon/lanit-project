package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.UserWithRolesProjection;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.dto.RoleDto;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.dto.UserWithRolesDto;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GetUsersWithRolesTest {
    private final static String USER_1 = "user1";
    private final static String USER_2 = "user2";
    private final static String USER_3 = "user3";
    private final static String ADMIN = "Admin";
    private final static String HR = "Hr";
    private final static String USER = "User";
    private final static String OMNI = "Omni";

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    GetUsersWithRolesUseCase getUsersWithRolesUseCase;

    @Test
    public void getRolesQuantity_singleRole() {
        List<UserWithRolesProjection> input = List.of(
            createUsersWithRolesProjection(USER_1, ADMIN),
            createUsersWithRolesProjection(USER_2, HR),
            createUsersWithRolesProjection(USER_3, USER)
        );
        List<UserWithRolesDto> expectedOutput = List.of(
            createUserWithRolesDto(USER_1, createRoleDto(ADMIN)),
            createUserWithRolesDto(USER_2, createRoleDto(HR)),
            createUserWithRolesDto(USER_3, createRoleDto(USER))
        );
        Mockito.when(employeeRepository.getUsersWithRoles()).thenReturn(input);

        Assert.assertEquals(expectedOutput, getUsersWithRolesUseCase.execute());
    }

    @Test
    public void getRolesQuantity_multipleRoles() {
        List<UserWithRolesProjection> input = List.of(
            createUsersWithRolesProjection(USER_1, ADMIN + " " + HR),
            createUsersWithRolesProjection(USER_2, HR),
            createUsersWithRolesProjection(USER_3, USER + " " + OMNI)
        );
        List<UserWithRolesDto> expectedOutput = List.of(
            createUserWithRolesDto(USER_1, createRoleDto(ADMIN), createRoleDto(HR)),
            createUserWithRolesDto(USER_2, createRoleDto(HR)),
            createUserWithRolesDto(USER_3, createRoleDto(USER), createRoleDto(OMNI))
        );
        Mockito.when(employeeRepository.getUsersWithRoles()).thenReturn(input);

        List<UserWithRolesDto> actualOutput = getUsersWithRolesUseCase.execute();

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private UserWithRolesProjection createUsersWithRolesProjection(String userCredentials, String roles) {
        UserWithRolesProjection projection = Mockito.mock(UserWithRolesProjection.class);
        Mockito.when(projection.getUserCredentials()).thenReturn(userCredentials);
        Mockito.when(projection.getRoles()).thenReturn(roles);
        return projection;
    }

    private RoleDto createRoleDto(String role) {
        return new RoleDto(role);
    }

    private UserWithRolesDto createUserWithRolesDto(String login, RoleDto... roles) {
        return new UserWithRolesDto(login, List.of(roles));
    }
}
