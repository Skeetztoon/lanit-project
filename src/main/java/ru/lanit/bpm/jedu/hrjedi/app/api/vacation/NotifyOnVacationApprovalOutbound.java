package ru.lanit.bpm.jedu.hrjedi.app.api.vacation;

import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

public interface NotifyOnVacationApprovalOutbound {
    void execute(Vacation approvedVacation);
}
