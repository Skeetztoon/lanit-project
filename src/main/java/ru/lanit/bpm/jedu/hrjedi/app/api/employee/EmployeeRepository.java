package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository {
    List<Employee> findAll();

    Employee save(Employee employee);

    List<Employee> saveAll(List<Employee> employees);

    Optional<Employee> findByLoginIgnoreCase(String login);

    boolean existsByLogin(String login);

    boolean existsByLogins(Set<String> logins);

    boolean existsByEmail(String email);

    boolean existsByEmails(Set<String> emails);
}
