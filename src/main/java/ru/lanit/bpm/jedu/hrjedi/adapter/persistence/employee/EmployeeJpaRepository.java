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
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginIgnoreCase(String login);

    boolean existsByLogin(String login);

    @Query("SELECT CASE WHEN COUNT(e)>0 then true ELSE false END " +
        "FROM Employee e WHERE e.login IN :logins")
    boolean existsByLogins(@Param("logins") Set<String> logins);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(e)>0 then true ELSE false END " +
        "FROM Employee e WHERE e.email IN :emails")
    boolean existsByEmails(@Param("emails") Set<String> emails);

}
