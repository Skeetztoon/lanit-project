package ru.lanit.bpm.jedu.hrjedi.app.api.vacation;

import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

public interface CreateVacationDocumentsOutbound {
    void execute(Vacation vacation) throws CreateVacationDocumentsException;
}
