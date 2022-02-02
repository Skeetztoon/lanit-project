package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;
import ru.lanit.bpm.jedu.hrjedi.staging.accessorutils.AdvancedAccessor;

@Component
@RequiredArgsConstructor
public class VacationApprovalProcessAccessor extends AdvancedAccessor {
    private static final String PROC_VAR_APPROVER_LOGIN = "approverLogin";
    private static final String PROC_VAR_INITIATOR_LOGIN = "initiatorLogin";
    private static final String PROC_VAR_LAST_ACTION = "lastAction";
    private static final String PROC_VAR_PROCESS_BUSINESS_KEY = "processBusinessKey";
    private static final String PROC_VAR_PROCESS_NAME = "processName";
    private static final String PROC_VAR_PROCESS_STATUS = "processStatus";
    private static final String PROC_VAR_VACATION = "vacation";

    private static final String TASK_VAR_ACTION = "action";
    private static final String TASK_VAR_INITIATOR_LOGIN = "initiatorLogin";
    private static final String TASK_VAR_PROCESS_NAME = "processName";
    private static final String TASK_VAR_STATUS = "status";
    private static final String TASK_VAR_VACATION = "vacation";

    private static final String ACTION_APPROVE = "approve";
    private static final String ACTION_SUBMIT = "submit";
    private static final String BUSINESS_KEY_PREFIX = "ОТПУСК-";
    private static final String PROCESS_NAME_PREFIX = "Согласование отпуска: ";

    private final IdentityService camundaIdentityService;

    public String getInitiatorLogin() {
        return camundaIdentityService.getCurrentAuthentication().getUserId();
    }

    public Vacation getVacation(DelegateTask task) {
        return getTaskVariable(task, TASK_VAR_VACATION);
    }

    public void setBusinessKey(DelegateExecution process) {
        String processBusinessKey = BUSINESS_KEY_PREFIX + process.getId();
        process.setProcessBusinessKey(processBusinessKey);
        process.setVariable(PROC_VAR_PROCESS_BUSINESS_KEY, processBusinessKey);
    }

    public void setInitiatorLogin(DelegateExecution process) {
        String initiatorLogin = getInitiatorLogin();
        process.setVariable(PROC_VAR_INITIATOR_LOGIN, initiatorLogin);
    }

    public void setApproverLogin(DelegateExecution process, String approverLogin) {
        process.setVariable(PROC_VAR_APPROVER_LOGIN, approverLogin);
    }

    public void setProcessName(DelegateExecution process, String initiatorFullName) {
        String processName = PROCESS_NAME_PREFIX + initiatorFullName;
        process.setVariable(PROC_VAR_PROCESS_NAME, processName);
    }

    public void setVacation(DelegateExecution process, Vacation vacation) {
        process.setVariable(PROC_VAR_VACATION, vacation);
    }

    public void setVacation(DelegateTask task) {
        mapFromTask(task, PROC_VAR_VACATION, TASK_VAR_VACATION);
    }

    public void setAction(DelegateTask task) {
        mapFromTask(task, PROC_VAR_LAST_ACTION, TASK_VAR_ACTION);
    }

    public void fillCommonTaskVariables(DelegateTask task) {
        mapToTask(task,
            TASK_VAR_PROCESS_NAME, PROC_VAR_PROCESS_NAME,
            TASK_VAR_STATUS, PROC_VAR_PROCESS_STATUS,
            TASK_VAR_INITIATOR_LOGIN, PROC_VAR_INITIATOR_LOGIN,
            TASK_VAR_VACATION, PROC_VAR_VACATION
        );
    }

    public boolean isApproveActionSelected(DelegateTask task) {
        return ACTION_APPROVE.equals(getTaskVariable(task, TASK_VAR_ACTION));
    }

    public boolean isSubmitActionSelected(DelegateTask task) {
        return ACTION_SUBMIT.equals(getTaskVariable(task, TASK_VAR_ACTION));
    }
}
