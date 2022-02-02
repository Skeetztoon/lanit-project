package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.AttendanceRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class AttendanceRepositoryAdapter implements AttendanceRepository {
    private final AttendanceJpaRepository attendanceJpaRepository;

    @Override
    public List<Attendance> findAllByEmployeeId(Long id) {
        return attendanceJpaRepository.findAllByEmployeeId(id);
    }

    @Override
    public Set<Integer> findMonthsValuesWithAttendanceInfoByYear(int year) {
        return attendanceJpaRepository.findMonthsValuesWithAttendanceInfoByYear(year);
    }

    @Override
    public List<Attendance> findAllByMonth(int year, int month) {
        return attendanceJpaRepository.findAllByMonth(year, month);
    }
}
