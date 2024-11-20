/*
 * Copyright (c) 2008-2020
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $
 */
package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.RoleQuantityProjection;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.projections.UserWithRolesProjection;

import java.util.List;
import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginIgnoreCase(String login);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    @Query("SELECT r.name AS role, COUNT(e.login) AS usersQuantity " +
        "FROM Employee e " +
        "JOIN e.roles r " +
        "GROUP BY r.name")
    List<RoleQuantityProjection> getRolesQuantity();

    @Query(value = "SELECT concat(e.last_name, ' ', e.first_name, ' ', e.patronymic) as userCredentials, " +
        "string_agg(r.name, ' ') AS roles " +
        "FROM Employee e " +
        "JOIN Employee_role er " +
        "ON e.id = er.employee_id " +
        "JOIN Role r " +
        "ON er.role_id = r.id " +
        "GROUP BY userCredentials", nativeQuery = true)
    List<UserWithRolesProjection> getUsersWithRoles();
}



