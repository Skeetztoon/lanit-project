package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.AttendanceRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.GetMonthsWithoutAttendanceByYearInbound;

import java.time.YearMonth;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.time.Month.JANUARY;
import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class GetMonthsWithoutAttendanceByYearUseCase implements GetMonthsWithoutAttendanceByYearInbound {
    private static final int NUMBER_OF_MONTH_IN_YEAR = 12;

    private final DateTimeUtils dateTimeUtils;
    private final AttendanceRepository attendanceRepository;

    @Transactional(readOnly = true)
    @Override
    public List<YearMonth> execute(int year) {
        YearMonth currentMonth = dateTimeUtils.getCurrentMonth();
        int currentYear = currentMonth.getYear();

        if (year > currentYear || YearMonth.of(year, JANUARY).equals(currentMonth)) {
            return emptyList();
        }

        int numberOfMonthForWhichAttendanceInfoRequired = year == currentYear ? currentMonth.getMonthValue() - 1 : NUMBER_OF_MONTH_IN_YEAR;
        Set<Integer> monthsValuesWithAttendanceInfo = attendanceRepository.findMonthsValuesWithAttendanceInfoByYear(year);

        return IntStream.rangeClosed(1, numberOfMonthForWhichAttendanceInfoRequired)
            .filter(requiredMonth -> !monthsValuesWithAttendanceInfo.contains(requiredMonth))
            .mapToObj(requiredMonthWithoutInfo -> YearMonth.of(year, requiredMonthWithoutInfo))
            .toList();
    }
}
