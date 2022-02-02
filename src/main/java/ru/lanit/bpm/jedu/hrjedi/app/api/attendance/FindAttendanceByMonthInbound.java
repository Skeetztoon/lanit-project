package ru.lanit.bpm.jedu.hrjedi.app.api.attendance;

import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.time.YearMonth;
import java.util.List;

public interface FindAttendanceByMonthInbound {
    List<Attendance> execute(YearMonth year);
}
