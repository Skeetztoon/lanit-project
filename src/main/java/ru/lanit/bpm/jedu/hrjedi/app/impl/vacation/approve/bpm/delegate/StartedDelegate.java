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
package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindHeadOfHrEmployeeInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetEmployeeFullNameInbound;
import ru.lanit.bpm.jedu.hrjedi.app.impl.attendance.DateTimeUtils;
import ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm.VacationApprovalProcessAccessor;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

import java.time.LocalDate;

/**
 * Vacation Approval process start handler
 */
@Component("vacationApprovalStartedDelegate")
@RequiredArgsConstructor
public class StartedDelegate implements JavaDelegate {
    private final VacationApprovalProcessAccessor accessor;

    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;
    private final FindHeadOfHrEmployeeInbound findHeadOfHrEmployeeInbound;
    private final GetEmployeeFullNameInbound getEmployeeFullNameInbound;
    private final DateTimeUtils dateTimeUtils;

    @Override
    public void execute(DelegateExecution process) {
        String initiatorLogin = accessor.getInitiatorLogin();

        Employee employee = findEmployeeByLoginInbound.execute(initiatorLogin);
        String initiatorFullName = getEmployeeFullNameInbound.execute(employee);
        Vacation vacation = createDefaultVacationForEmployee(employee);

        Employee approver = findHeadOfHrEmployeeInbound.execute();

        accessor.setBusinessKey(process);
        accessor.setInitiatorLogin(process);
        accessor.setApproverLogin(process, approver.getLogin());
        accessor.setProcessName(process, initiatorFullName);
        accessor.setVacation(process, vacation);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private Vacation createDefaultVacationForEmployee(Employee employee) {
        Vacation vacation = new Vacation();
        vacation.setEmployee(employee);

        LocalDate currentDate = dateTimeUtils.getCurrentDate();
        vacation.setStart(currentDate.plusWeeks(2));
        vacation.setEnd(currentDate.plusWeeks(2).plusDays(7));

        return vacation;
    }
}
