package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;
import ru.lanit.bpm.jedu.hrjedi.staging.datetimeutils.DateTimeUtils;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CreateDefaultVacation {
    private final DateTimeUtils dateTimeUtils;

    @Transactional
    public Vacation execute(Employee employee) {
        Vacation vacation = new Vacation();
        vacation.setEmployee(employee);

        LocalDate currentDate = dateTimeUtils.getCurrentDate();
        vacation.setStart(currentDate.plusWeeks(2));
        vacation.setEnd(currentDate.plusWeeks(2).plusDays(7));

        return vacation;
    }
}
