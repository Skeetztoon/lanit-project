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

package ru.lanit.bpm.jedu.hrjedi.adapter.rest.employee;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.*;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;

import javax.servlet.ServletContext;

@RunWith(SpringRunner.class)
public class EmployeeControllerEmailUpdateTest {

    public static final String USER = "User";
    public static final ResponseEntity<String> EMAIL_IS_EMPTY = ResponseEntity.badRequest().body("Email is empty");
    public static final ResponseEntity<String> EMAIL_IS_INVALID = ResponseEntity.badRequest().body("Email is invalid");
    public static final ResponseEntity<String> EMAIL_CHANGED = ResponseEntity.ok("Email changed!");

    @Mock
    private UpdateEmployeeEmailInbound updateEmployeeEmailInbound;
    @Mock
    private CreateEmployeeInbound createEmployeeInbound;
    @Mock
    private FindEmployeeByLoginInbound findEmployeeByLoginInbound;
    @Mock
    private FindAllEmployeesInbound findAllEmployeesInbound;
    @Mock
    private GetEmployeeFullNameByLoginInbound getEmployeeFullNameByLoginInbound;
    @Mock
    private GetEmployeeAvatarInbound getEmployeeAvatarInbound;
    @Mock
    private GenerateSecurePasswordInbound generateSecurePasswordInbound;
    @Mock
    private ServletContext servletContext;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void emailUpdate_validLatin() {

        String body = "101.la@mail.com";

        Assert.assertEquals(
            EMAIL_CHANGED, employeeController.updateEmail(USER, body)
        );
    }

    @Test
    public void emailUpdate_validCyrilic() {

        String body = "привет-мир@mail.com";

        Assert.assertEquals(
            EMAIL_CHANGED, employeeController.updateEmail(USER, body)
        );
    }

    @Test
    public void emailUpdate_twoAtSymbols() {

        String body = "101.la@@mail.com";

        Assert.assertEquals(
            EMAIL_IS_INVALID, employeeController.updateEmail(USER, body)
        );
    }

    @Test
    public void emailUpdate_empty() {

        String body = "";

        Assert.assertEquals(
            EMAIL_IS_EMPTY, employeeController.updateEmail(USER, body)
        );
    }
}
