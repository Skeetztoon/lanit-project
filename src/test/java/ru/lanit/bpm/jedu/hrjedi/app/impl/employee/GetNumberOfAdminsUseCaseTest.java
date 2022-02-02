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
package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;

import java.util.Collection;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName.*;

@RunWith(MockitoJUnitRunner.class)
public class GetNumberOfAdminsUseCaseTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    GetNumberOfAdminsUseCase getNumberOfAdminsUseCase;

    @Test
    public void getNumberOfAdmins() {
        Mockito.when(employeeRepository.findAll()).thenReturn(asList(
            user(singleton(role(ROLE_USER))),
            user(singleton(role(ROLE_OMNI))),
            user(asList(role(ROLE_USER), role(ROLE_OMNI))),
            user(asList(role(ROLE_USER), role(ROLE_ADMIN))),
            user(singleton(role(ROLE_ADMIN)))
        ));

        long numberOfAdmins = getNumberOfAdminsUseCase.execute();

        assertEquals(2, numberOfAdmins);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private Employee user(Collection<Role> roles) {
        Employee user = new Employee();
        user.setRoles(new HashSet<>(roles));

        return user;
    }

    private Role role(RoleName name) {
        Role role = new Role();
        role.setName(name);

        return role;
    }
}
