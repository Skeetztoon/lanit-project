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
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.*;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UpdateEmployeeEmailTest {

    private static final String USER = "User";

   @Mock
   EmployeeRepository employeeRepository;

    @Mock
    FindEmployeeByLoginInbound findEmployeeByLoginInbound;

   @InjectMocks
   UpdateEmployeeEmailUseCase updateEmployeeEmailUseCase;


    @Test
    public void emailUpdate_validLatinWithDash() {
        String email = "asd-123@gmad.ti";
        Employee employee = new Employee();
        employee.setLogin(USER);

        Mockito.when(findEmployeeByLoginInbound.execute(USER)).thenReturn(employee);

        updateEmployeeEmailUseCase.execute(USER, email);

        assertEquals(email, employee.getEmail());
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    public void emailUpdate_validCyrilicWithDot() {
        String email = "привет.мир@почта.ру";
        Employee employee = new Employee();
        employee.setLogin(USER);

        Mockito.when(findEmployeeByLoginInbound.execute(USER)).thenReturn(employee);

        updateEmployeeEmailUseCase.execute(USER, email);

        assertEquals(email, employee.getEmail());
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    public void emailUpdate_invalidNoPrefix() {
        String email = "@mail.ti";
        Employee employee = new Employee();
        employee.setLogin(USER);

        Mockito.when(findEmployeeByLoginInbound.execute(USER)).thenReturn(employee);

        assertThrows(InvalidEmailException.class, () -> updateEmployeeEmailUseCase.execute(USER, email));
    }

    @Test
    public void emailUpdate_invalidNoPostfix() {
        String email = "asd@";
        Employee employee = new Employee();
        employee.setLogin(USER);

        Mockito.when(findEmployeeByLoginInbound.execute(USER)).thenReturn(employee);

        assertThrows(InvalidEmailException.class, () -> updateEmployeeEmailUseCase.execute(USER, email));
    }

    @Test
    public void emailUpdate_invalidDoubleAt() {
        String email = "asd@@mail.com";
        Employee employee = new Employee();
        employee.setLogin(USER);

        Mockito.when(findEmployeeByLoginInbound.execute(USER)).thenReturn(employee);

        assertThrows(InvalidEmailException.class, () -> updateEmployeeEmailUseCase.execute(USER, email));
    }
}