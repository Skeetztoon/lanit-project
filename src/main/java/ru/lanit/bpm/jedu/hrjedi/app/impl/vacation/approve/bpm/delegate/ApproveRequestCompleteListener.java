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

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.adapter.email.EmailNotificationController;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.VacationRepository;
import ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm.VacationApprovalProcessAccessor;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

@Component("vacationApprovalApproveRequestCompleteListener")
public class ApproveRequestCompleteListener extends CommonTaskCompleteListener {
    private final EmailNotificationController notificationController;
    private final VacationRepository vacationRepository;

    public ApproveRequestCompleteListener(VacationApprovalProcessAccessor accessor,
        EmailNotificationController notificationController, VacationRepository vacationRepository) {
        super(accessor);
        this.notificationController = notificationController;
        this.vacationRepository = vacationRepository;
    }

    @Override
    public void notify(DelegateTask task) {
        super.notify(task);
        if (accessor.isApproveActionSelected(task)) {
            Vacation approvedVacation = accessor.getVacation(task);
            vacationRepository.save(approvedVacation);
            notificationController.notifyOnVacationApproval(approvedVacation);
        }
    }
}
