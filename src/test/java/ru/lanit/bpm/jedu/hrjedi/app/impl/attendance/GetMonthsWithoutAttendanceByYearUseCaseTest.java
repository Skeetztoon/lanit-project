/*
 * Copyright (c) 2008-2020
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $
 */
package ru.lanit.bpm.jedu.hrjedi.app.impl.attendance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.attendance.AttendanceRepository;
import ru.lanit.bpm.jedu.hrjedi.staging.datetimeutils.DateTimeUtils;

import java.time.Month;
import java.time.YearMonth;

import static java.util.Collections.emptyList;
import static java.util.Arrays.asList;
import java.util.List;

import static org.apache.commons.collections4.SetUtils.hashSet;
import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class GetMonthsWithoutAttendanceByYearUseCaseTest {

    private static final int YEAR_2020 = 2020;
    private static final int YEAR_2021 = 2021;
    private static final YearMonth JANUARY_2020 = YearMonth.of(YEAR_2020, Month.JANUARY);
    private static final YearMonth NOVEMBER_2020 = YearMonth.of(YEAR_2020, Month.NOVEMBER);
    private static final YearMonth JANUARY_2021 = YearMonth.of(YEAR_2021, Month.JANUARY);

    @Mock
    private DateTimeUtils dateTimeUtils;
    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    protected GetMonthsWithoutAttendanceByYearUseCase getMonthsWithoutAttendanceByYearUseCase;

    public List<YearMonth> monthsWithoutAttendanceResult(int year) {
        return getMonthsWithoutAttendanceByYearUseCase.execute(year);
    }

    private void setCurrentMonth(YearMonth yearMonth) {
        Mockito.when(dateTimeUtils.getCurrentMonth()).thenReturn(yearMonth);
    }

    private void mockAttendanceData(int year, java.util.Set<Integer> monthNumbers) {
        Mockito.when(attendanceRepository.findMonthsValuesWithAttendanceInfoByYear(year)).thenReturn(monthNumbers);
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_currentYear() {
        setCurrentMonth(NOVEMBER_2020);
        mockAttendanceData(YEAR_2020, hashSet(1, 3, 5, 7, 8, 9));

        assertEquals(asList(
            YearMonth.of(YEAR_2020, Month.FEBRUARY),
            YearMonth.of(YEAR_2020, Month.APRIL),
            YearMonth.of(YEAR_2020, Month.JUNE),
            YearMonth.of(YEAR_2020, Month.OCTOBER)
        ), monthsWithoutAttendanceResult(YEAR_2020));
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_currentYear_allRequiredMonthWithAttendanceInfo() {
        setCurrentMonth(NOVEMBER_2020);
        mockAttendanceData(YEAR_2020, hashSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        assertEquals(emptyList(), monthsWithoutAttendanceResult(YEAR_2020));
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_futureYear() {
        setCurrentMonth(NOVEMBER_2020);

        assertEquals(emptyList(), monthsWithoutAttendanceResult(YEAR_2021));
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_futureCurrentJanuary() {
        setCurrentMonth(JANUARY_2020);

        assertEquals(emptyList(), monthsWithoutAttendanceResult(YEAR_2020));
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_forPastYear() {
        setCurrentMonth(JANUARY_2021);
        mockAttendanceData(YEAR_2020, hashSet(1, 3, 5, 7, 8, 9, 11));

        assertEquals(asList(
            YearMonth.of(YEAR_2020, Month.FEBRUARY),
            YearMonth.of(YEAR_2020, Month.APRIL),
            YearMonth.of(YEAR_2020, Month.JUNE),
            YearMonth.of(YEAR_2020, Month.OCTOBER),
            YearMonth.of(YEAR_2020, Month.DECEMBER)
        ), monthsWithoutAttendanceResult(YEAR_2020));
    }
}
