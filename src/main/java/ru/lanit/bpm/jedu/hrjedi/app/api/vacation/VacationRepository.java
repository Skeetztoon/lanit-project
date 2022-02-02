package ru.lanit.bpm.jedu.hrjedi.app.api.vacation;

import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

public interface VacationRepository {

    void save(Vacation vacation);
}
