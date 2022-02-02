package ru.lanit.bpm.jedu.hrjedi.app.api.attendance;

import org.apache.poi.ss.usermodel.Workbook;

import java.time.Month;

public interface CreateAttendanceReportInbound {
    Workbook execute(Month month, int year);
}
