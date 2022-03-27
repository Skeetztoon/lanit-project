package ru.lanit.bpm.jedu.hrjedi.adapter.accounting;

import org.springframework.stereotype.Controller;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.CreateVacationDocumentsException;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.CreateVacationDocumentsOutbound;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

@Controller
public class CreateVacationDocumentsAdapter implements CreateVacationDocumentsOutbound {
    public void execute(Vacation vacation) throws CreateVacationDocumentsException {
        // TODO: Добавить реализацию
        throw new CreateVacationDocumentsException("Not implemented yet");
    }
}
