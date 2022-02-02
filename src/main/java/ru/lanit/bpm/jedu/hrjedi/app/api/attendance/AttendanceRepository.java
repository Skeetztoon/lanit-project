package ru.lanit.bpm.jedu.hrjedi.app.api.attendance;

import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.util.List;
import java.util.Set;

public interface AttendanceRepository {
    List<Attendance> findAllByEmployeeId(Long id);

    Set<Integer> findMonthsValuesWithAttendanceInfoByYear(int year);

    List<Attendance> findAllByMonth(int year, int month);
}
