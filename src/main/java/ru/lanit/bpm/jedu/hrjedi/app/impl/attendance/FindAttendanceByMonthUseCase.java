package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.AttendanceRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.FindAttendanceByMonthInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAttendanceByMonthUseCase implements FindAttendanceByMonthInbound {
    private final AttendanceRepository attendanceRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Attendance> execute(YearMonth yearMonth) {
        return attendanceRepository.findAllByMonth(yearMonth.getYear(), yearMonth.getMonthValue());
    }
}
