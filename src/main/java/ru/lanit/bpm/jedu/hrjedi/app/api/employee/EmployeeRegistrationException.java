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
package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import java.io.Serial;

public class EmployeeRegistrationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1633754699258321987L;

    public EmployeeRegistrationException(String message) {
        super(message);
    }
}
