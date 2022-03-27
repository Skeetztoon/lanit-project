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
import ru.lanit.bpm.jedu.hrjedi.app.impl.employee.FindHeadOfHrEmployee;
import ru.lanit.bpm.jedu.hrjedi.app.impl.employee.GetEmployeeFullName;
import ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.CreateDefaultVacation;
import ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm.VacationApprovalProcessAccessor;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

/**
 * Vacation Approval process start handler
 */
@Component("vacationApprovalStartedDelegate")
@RequiredArgsConstructor
public class StartedDelegate implements JavaDelegate {
    private final VacationApprovalProcessAccessor accessor;

    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;
    private final FindHeadOfHrEmployee findHeadOfHrEmployee;
    private final GetEmployeeFullName getEmployeeFullName;
    private final CreateDefaultVacation createDefaultVacation;

    @Override
    public void execute(DelegateExecution process) {
        String initiatorLogin = accessor.getInitiatorLogin();

        Employee employee = findEmployeeByLoginInbound.execute(initiatorLogin);
        String initiatorFullName = getEmployeeFullName.execute(employee);
        Vacation vacation = createDefaultVacation.execute(employee);

        Employee approver = findHeadOfHrEmployee.execute();

        accessor.setBusinessKey(process);
        accessor.setInitiatorLogin(process);
        accessor.setApproverLogin(process, approver.getLogin());
        accessor.setProcessName(process, initiatorFullName);
        accessor.setVacation(process, vacation);
    }
}
