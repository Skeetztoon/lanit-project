package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.RoleQuantityProjection;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.UserWithRolesProjection;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();

    Employee save(Employee employee);

    Optional<Employee> findByLoginIgnoreCase(String login);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    List<RoleQuantityProjection> getRolesQuantity();

    List<UserWithRolesProjection> getUsersWithRoles();
}
