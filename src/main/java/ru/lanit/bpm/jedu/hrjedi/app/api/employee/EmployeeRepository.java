package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();

    Employee save(Employee employee);

    Optional<Employee> findByLoginIgnoreCase(String login);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    List<Map<String, Object>> getRolesQuantity();

    List<Map<String, Object>> getUsersWithRoles();
}
