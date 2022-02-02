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
import ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm.VacationApprovalProcessAccessor;

@Component("vacationApprovalPrepareRequestCompleteListener")
public class PrepareRequestCompleteListener extends CommonTaskCompleteListener {

    public PrepareRequestCompleteListener(VacationApprovalProcessAccessor accessor) {
        super(accessor);
    }

    @Override
    public void notify(DelegateTask task) {
        super.notify(task);

        if (accessor.isSubmitActionSelected(task)) {
            accessor.setVacation(task);
        }
    }
}
