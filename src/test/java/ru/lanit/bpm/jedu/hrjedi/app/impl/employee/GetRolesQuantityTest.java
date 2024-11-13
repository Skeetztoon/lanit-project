package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class GetRolesQuantityTest {

    private final static String ROLE = "ROLE";
    private final static String USERS_QUANTITY = "USERS_QUANTITY";

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    GetRolesQuantityUseCase getRolesQuantityUseCase;

    @Test
    public void getRolesQuantity_singleRole() {
        List<Map<String, Object>> roles = new ArrayList<>();
        roles.add(Map.of(ROLE, "admin", USERS_QUANTITY, BigInteger.valueOf(1)));
        roles.add(Map.of(ROLE, "hr", USERS_QUANTITY, BigInteger.valueOf(2)));
        roles.add(Map.of(ROLE, "user", USERS_QUANTITY, BigInteger.valueOf(10)));
        Map<String, Long> expectedOutput = new HashMap<>();
        expectedOutput.put("admin",  1L);
        expectedOutput.put("hr", 2L);
        expectedOutput.put("user", 10L);

        Mockito.when(employeeRepository.getRolesQuantity()).thenReturn(roles);

        Assert.assertEquals(expectedOutput, getRolesQuantityUseCase.execute());
    }
}
