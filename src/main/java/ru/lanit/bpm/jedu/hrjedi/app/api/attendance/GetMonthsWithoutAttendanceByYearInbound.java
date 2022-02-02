package ru.lanit.bpm.jedu.hrjedi.app.api.attendance;

import java.time.YearMonth;
import java.util.List;

public interface GetMonthsWithoutAttendanceByYearInbound {
    List<YearMonth> execute(int year);
}
