package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.RoleQuantityProjection;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.UserWithRolesProjection;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {
    private final EmployeeJpaRepository employeeJpaRepository;

    @Override
    public List<Employee> findAll() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    @Override
    public Optional<Employee> findByLoginIgnoreCase(String login) {
        return employeeJpaRepository.findByLoginIgnoreCase(login);
    }

    @Override
    public boolean existsByLogin(String login) {
        return employeeJpaRepository.existsByLogin(login);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeJpaRepository.existsByEmail(email);
    }

    @Override
    public List<RoleQuantityProjection> getRolesQuantity() {
        return employeeJpaRepository.getRolesQuantity();
    }

    @Override
    public List<UserWithRolesProjection> getUsersWithRoles() {
        return employeeJpaRepository.getUsersWithRoles();
    }
}
