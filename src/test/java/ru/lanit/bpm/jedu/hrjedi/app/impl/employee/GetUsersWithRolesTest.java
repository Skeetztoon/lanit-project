package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class GetUsersWithRolesTest {

    private final static String LOGIN = "login";
    private final static String ROLES = "roles";
    private final static String ADMIN = "Admin";
    private final static String HR = "Hr";
    private final static String USER = "User";

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    GetUsersWithRolesUseCase getUsersWithRolesUseCase;

    @Test
    public void getRolesQuantity_singleRole() {
        List<Map<String, Object>> roles = new ArrayList<>();
        roles.add(Map.of(LOGIN, "user1", ROLES, ADMIN));
        roles.add(Map.of(LOGIN, "user2", ROLES, HR));
        roles.add(Map.of(LOGIN, "user3", ROLES, USER));
        List<Map<String, Object>> expectedOutput = new ArrayList<>();
        expectedOutput.add(Map.of(LOGIN, "user1", ROLES, asList(Map.of("name", ADMIN))));
        expectedOutput.add(Map.of(LOGIN, "user2", ROLES, asList(Map.of("name", HR))));
        expectedOutput.add(Map.of(LOGIN, "user3", ROLES, asList(Map.of("name", USER))));

        Mockito.when(employeeRepository.getUsersWithRoles()).thenReturn(roles);

        Assert.assertEquals(expectedOutput, getUsersWithRolesUseCase.execute());
    }

    @Test
    public void getRolesQuantity_multipleRoles() {
        List<Map<String, Object>> roles = new ArrayList<>();
        roles.add(Map.of(LOGIN, "user1", ROLES, ADMIN+" "+HR));
        roles.add(Map.of(LOGIN, "user2", ROLES, HR));
        roles.add(Map.of(LOGIN, "user3", ROLES, USER));
        List<Map<String, Object>> expectedOutput = new ArrayList<>();
        expectedOutput.add(Map.of(LOGIN, "user1", ROLES, asList(Map.of("name", ADMIN), Map.of("name", HR))));
        expectedOutput.add(Map.of(LOGIN, "user2", ROLES, asList(Map.of("name", HR))));
        expectedOutput.add(Map.of(LOGIN, "user3", ROLES, asList(Map.of("name", USER))));

        Mockito.when(employeeRepository.getUsersWithRoles()).thenReturn(roles);

        Assert.assertEquals(expectedOutput, getUsersWithRolesUseCase.execute());
    }
}
