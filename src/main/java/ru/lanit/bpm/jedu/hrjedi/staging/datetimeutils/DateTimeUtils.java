package ru.lanit.bpm.jedu.hrjedi.staging.datetimeutils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class DateTimeUtils {
    /**
     * Возвращает текущий день. Необходим для тестирования, чтобы мокировать получение текущего дня.
     *
     * @return текущий день
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Возвращает текущий месяц. Необходим для тестирования, чтобы мокировать получение текущего месяца.
     *
     * @return текущий месяц
     */
    public YearMonth getCurrentMonth() {
        return YearMonth.now();
    }
}
