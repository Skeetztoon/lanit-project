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
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginIgnoreCase(String login);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
