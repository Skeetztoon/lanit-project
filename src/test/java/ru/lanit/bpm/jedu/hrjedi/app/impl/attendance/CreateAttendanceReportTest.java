package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.adapter.persistence.attendance.AttendanceRepositoryAdapter;
import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.office.Office;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAttendanceReportTest {
    private final static int YEAR = 2024;
    private final static LocalDateTime exitTime = LocalDateTime.of(YEAR, Month.NOVEMBER, 18, 18, 15, 00);
    private final static LocalDateTime entranceTime = LocalDateTime.of(YEAR, Month.NOVEMBER, 18, 10, 35, 00);

    @Mock
    private AttendanceRepositoryAdapter attendanceRepositoryAdapter;

    @InjectMocks
    private CreateAttendanceReportUseCase createAttendanceReportUseCase;

    @Test
    public void sortByOffice() {
        List<Attendance> attendanceList = new ArrayList<>(List.of(
            mockAttendance("ivan", "ivanov", null, "Севастополь"),
            mockAttendance("petr", "petrov", null, "Москва")
        ));

        when(attendanceRepositoryAdapter.findAllByMonth(YEAR, Month.NOVEMBER.getValue())).thenReturn(attendanceList);

        createAttendanceReportUseCase.execute(Month.NOVEMBER, YEAR);

        verify(attendanceRepositoryAdapter).findAllByMonth(YEAR, Month.NOVEMBER.getValue());
        assertEquals("Москва", attendanceList.get(0).getOffice().getName());
        assertEquals("Севастополь", attendanceList.get(1).getOffice().getName());
    }

    @Test
    public void sortByLastName() {
        List<Attendance> attendanceList = new ArrayList<>(List.of(
            mockAttendance("petr", "petrov", null, "Москва"),
            mockAttendance("ivan", "ivanov", null, "Москва")
        ));

        when(attendanceRepositoryAdapter.findAllByMonth(YEAR, Month.NOVEMBER.getValue())).thenReturn(attendanceList);

        createAttendanceReportUseCase.execute(Month.NOVEMBER, YEAR);

        verify(attendanceRepositoryAdapter).findAllByMonth(YEAR, Month.NOVEMBER.getValue());
        assertEquals("ivanov", attendanceList.get(0).getEmployee().getLastName());
        assertEquals("petrov", attendanceList.get(1).getEmployee().getLastName());
    }

    @Test
    public void sortByFirstName() {
        List<Attendance> attendanceList = new ArrayList<>(List.of(
            mockAttendance("petr", "petrov", null, "Москва"),
            mockAttendance("ivan", "petrov", null, "Москва")
        ));

        when(attendanceRepositoryAdapter.findAllByMonth(YEAR, Month.NOVEMBER.getValue())).thenReturn(attendanceList);

        createAttendanceReportUseCase.execute(Month.NOVEMBER, YEAR);

        verify(attendanceRepositoryAdapter).findAllByMonth(YEAR, Month.NOVEMBER.getValue());
        assertEquals("ivan", attendanceList.get(0).getEmployee().getFirstName());
        assertEquals("petr", attendanceList.get(1).getEmployee().getFirstName());
    }

    @Test
    public void calculateDurationInHours() {
        List<Attendance> attendanceList = new ArrayList<>(List.of(
            mockAttendance("petr", "petrov", null, "Москва")
        ));
        when(attendanceRepositoryAdapter.findAllByMonth(YEAR, Month.NOVEMBER.getValue())).thenReturn(attendanceList);

        Workbook workbook = createAttendanceReportUseCase.execute(Month.NOVEMBER, YEAR);

        Sheet sheet1 = workbook.getSheetAt(0);
        Row row = sheet1.getRow(3);
        int duration = (int) row.getCell(1).getNumericCellValue();

        assertEquals(8, duration);
    }

    @Test
    public void credentialsConcatenation() {
        List<Attendance> attendanceList = new ArrayList<>(List.of(
            mockAttendance("petr", "petrov", "test", "Москва")
        ));
        when(attendanceRepositoryAdapter.findAllByMonth(YEAR, Month.NOVEMBER.getValue())).thenReturn(attendanceList);

        Workbook workbook = createAttendanceReportUseCase.execute(Month.NOVEMBER, YEAR);

        Sheet sheet1 = workbook.getSheetAt(0);
        Row row = sheet1.getRow(3);
        String credentials = row.getCell(0).getStringCellValue();

        assertEquals("petrov petr test", credentials);
    }

    @Test
    public void credentialsWithoutPatronymicConcatenation() {
        List<Attendance> attendanceList = new ArrayList<>(List.of(
            mockAttendance("petr", "petrov", null, "Москва")
        ));
        when(attendanceRepositoryAdapter.findAllByMonth(YEAR, Month.NOVEMBER.getValue())).thenReturn(attendanceList);

        Workbook workbook = createAttendanceReportUseCase.execute(Month.NOVEMBER, YEAR);

        Sheet sheet1 = workbook.getSheetAt(0);
        Row row = sheet1.getRow(3);
        String credentials = row.getCell(0).getStringCellValue();

        assertEquals("petrov petr", credentials);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private Attendance mockAttendance(String firstName, String lastName, String patronymic, String officeName) {
        Attendance attendance = Mockito.mock(Attendance.class);
        Employee employee = Mockito.mock(Employee.class);
        Office office = Mockito.mock(Office.class);

        when(office.getName()).thenReturn(officeName);
        when(employee.getFirstName()).thenReturn(firstName);
        when(employee.getLastName()).thenReturn(lastName);
        when(employee.getPatronymic()).thenReturn(patronymic);

        when(attendance.getEmployee()).thenReturn(employee);
        when(attendance.getOffice()).thenReturn(office);
        when(attendance.getEntranceTime()).thenReturn(entranceTime);
        when(attendance.getExitTime()).thenReturn(exitTime);

        return attendance;
    }
}
