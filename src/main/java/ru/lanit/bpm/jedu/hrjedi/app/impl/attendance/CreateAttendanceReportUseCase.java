package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.adapter.persistence.attendance.AttendanceRepositoryAdapter;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.CreateAttendanceReportInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Month;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CreateAttendanceReportUseCase implements CreateAttendanceReportInbound {

    private final AttendanceRepositoryAdapter attendanceRepositoryAdapter;

    @Transactional
    @Override
    public Workbook execute(Month month, int year) {
        InputStream attendanceTemplate = getClass().getResourceAsStream("/reports/attendance.xlsx");
        try {

            List<Attendance> attendanceList = attendanceRepositoryAdapter.findAllByMonth(year, month.getValue());

            List<String> officeOrder = List.of("Нижний Новгород", "Уфа", "Москва", "Севастополь");

            attendanceList.sort(Comparator.comparing((Attendance a) -> officeOrder.indexOf(a.getOffice().getName()))
                .thenComparing(a -> a.getEmployee().getLastName())
                .thenComparing(a -> a.getEmployee().getFirstName())
            );

            XSSFWorkbook workbook = new XSSFWorkbook(attendanceTemplate);
            Sheet sheet1 = workbook.getSheetAt(0);
            Sheet sheet2 = workbook.getSheetAt(1);

            Row monthYearRow = sheet1.getRow(0);
            Cell monthCell = monthYearRow.createCell(2);
            Cell yearCell = monthYearRow.createCell(4);
            monthCell.setCellValue(month.getValue());
            yearCell.setCellValue(year);

            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy hh:mm:ss"));

            Map<String, Integer> hoursByEmployee = new HashMap<>();
            Map<String, String> officeOfEmployee = new HashMap<>();
            Map<Long, String> officesInfo = new HashMap<>();

            for (int i = 0; i < attendanceList.size(); i++) {

                Attendance attendance = attendanceList.get(i);

                // данные для первого листа
                String credentials = getCredentials(attendance);
                int duration = getDurationInHours(attendance);
                hoursByEmployee.put(credentials, hoursByEmployee.getOrDefault(credentials, 0) + duration);
                officeOfEmployee.putIfAbsent(credentials, attendance.getOffice().getName());
                //

                // создание строки и ячеек на втором листе
                Row row = sheet2.createRow(i + 1);
                Cell credentialsCell = row.createCell(0);
                Cell entranceTimeCell = row.createCell(1);
                entranceTimeCell.setCellStyle(dateCellStyle);
                Cell exitTimeCell = row.createCell(2);
                exitTimeCell.setCellStyle(dateCellStyle);
                Cell officeIdCell = row.createCell(3);

                setRowData(attendance, credentialsCell, entranceTimeCell, exitTimeCell, officeIdCell);

                // данные для таблицы офисов
                if (!officesInfo.containsKey(attendance.getOffice().getId())) {
                    officesInfo.put(attendance.getOffice().getId(), attendance.getOffice().getName());
                }
            }

            // заполнение первого листа
            int firstListRowCounter = 3;
            for (Map.Entry<String, Integer> entry : hoursByEmployee.entrySet()) {

                Row row = sheet1.createRow(firstListRowCounter);
                Cell credentials = row.createCell(0);
                Cell duration = row.createCell(1);
                Cell officeName = row.createCell(2);

                credentials.setCellValue(entry.getKey());
                duration.setCellValue(entry.getValue());
                officeName.setCellValue(officeOfEmployee.get(entry.getKey()));

                firstListRowCounter++;
            }

            // заполнение таблицы офисов
            int secondListRowCounter = 1;
            for (Map.Entry<Long, String> entry : officesInfo.entrySet()) {

                Row row = sheet2.getRow(secondListRowCounter);
                Cell officeId = row.createCell(5);
                Cell officeName = row.createCell(6);

                officeId.setCellValue(entry.getKey());
                officeName.setCellValue(entry.getValue());

                secondListRowCounter++;
            }

            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            return workbook;
        } catch (IOException e) {
            throw new IllegalStateException("Error in attendance report", e);
        }
    }

    //=================================
    // Implementation
    //=================================
    private String getCredentials(Attendance attendance) {
        return attendance.getEmployee().getLastName() + " " +
            attendance.getEmployee().getFirstName() + " " +
            attendance.getEmployee().getPatronymic();
    }

    private void setRowData(Attendance attendance, Cell credentials, Cell entrance, Cell exit, Cell officeId) {
        credentials.setCellValue(getCredentials(attendance));
        entrance.setCellValue(attendance.getEntranceTime());
        exit.setCellValue(attendance.getExitTime());
        officeId.setCellValue(attendance.getOffice().getId());
    }

    private int getDurationInHours(Attendance attendance) {
        Duration duration = Duration.between(attendance.getEntranceTime(), attendance.getExitTime());
        long seconds = duration.getSeconds();
        return (int) Math.ceil((double) seconds / 3600);
    }
}
