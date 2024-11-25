package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation.approve.bpm.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.adapter.accounting.CreateVacationDocumentsAdapter;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.CreateVacationDocumentsException;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

@Component("createVacationDocumentsDelegate")
@RequiredArgsConstructor
public class CreateVacationDocumentsDelegate implements JavaDelegate {
    private static final String SENDING_STATUS = "sendingStatus";
    private static final String DECLINE = "decline";
    private static final String ACCEPT = "accept";

    private final CreateVacationDocumentsAdapter adapter;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Vacation vacation = (Vacation) delegateExecution.getVariable("vacation");
        if (vacation != null) {
            try {
                adapter.execute(vacation);
                delegateExecution.setVariable(SENDING_STATUS, ACCEPT);
            } catch (CreateVacationDocumentsException e) {
                delegateExecution.setVariable(SENDING_STATUS, DECLINE);
            }
        } else {
            delegateExecution.setVariable(SENDING_STATUS, DECLINE);
            throw new IllegalStateException("Vacation is missing");
        }
    }
}
