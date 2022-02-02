package ru.lanit.bpm.jedu.hrjedi.app.api.attendance;

import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.util.List;

public interface FindAttendanceByEmployeeIdInbound {
    List<Attendance> execute(Long employeeId);
}
