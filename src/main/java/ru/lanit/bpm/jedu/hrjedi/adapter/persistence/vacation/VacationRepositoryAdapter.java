package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.VacationRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

@Repository
@RequiredArgsConstructor
public class VacationRepositoryAdapter implements VacationRepository {
    private final VacationJpaRepository vacationJpaRepository;

    @Override
    public void save(Vacation vacation) {
        vacationJpaRepository.save(vacation);
    }
}
