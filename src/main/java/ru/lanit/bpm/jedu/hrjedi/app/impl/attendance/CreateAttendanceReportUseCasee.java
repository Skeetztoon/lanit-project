package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.CreateAttendanceReportInbound;

import java.io.IOException;
import java.io.InputStream;
import java.time.Month;

@Component
@RequiredArgsConstructor
public class CreateAttendanceReportUseCasee implements CreateAttendanceReportInbound {

    @Override
    public Workbook execute(Month month, int year) {
        InputStream attendanceTemplate = getClass().getResourceAsStream("/reports/attendance.xlsx");
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(attendanceTemplate);

            // now we just downloading empty report

            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            return workbook;
        } catch (IOException e) {
            throw new IllegalStateException("Error in attendance report", e);
        }
    }
}
