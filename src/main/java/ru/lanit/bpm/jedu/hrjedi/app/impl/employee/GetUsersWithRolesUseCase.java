package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetUsersWithRolesInbound;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetUsersWithRolesUseCase implements GetUsersWithRolesInbound {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> execute() {
        List<Map<String, Object>> rawData = employeeRepository.getUsersWithRoles();
        return transformData(rawData);
    }

    private List<Map<String, Object>> transformData (List<Map<String, Object>> input) {
        List<Map<String, Object>> output = new ArrayList<>();

        for (Map<String, Object> user : input) {
            String login = (String) user.get("login");
            String roles = (String) user.get("roles");

            List<Map<String, String>> rolesList = new ArrayList<>();

            for (String role : roles.split(" ")) {
                Map<String, String> roleMap = new HashMap<>();
                roleMap.put("name", role);
                rolesList.add(roleMap);
            }

            Map<String, Object> transformedUser = new HashMap<>();
            transformedUser.put("login", login);
            transformedUser.put("roles", rolesList);
            output.add(transformedUser);
        }
        return  output;
    }
}
