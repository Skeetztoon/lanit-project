package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.AttendanceRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.FindAttendanceByEmployeeIdInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAttendanceByEmployeeIdUseCase implements FindAttendanceByEmployeeIdInbound {
    private final AttendanceRepository attendanceRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Attendance> execute(Long employeeId) {
        return attendanceRepository.findAllByEmployeeId(employeeId);
    }
}
