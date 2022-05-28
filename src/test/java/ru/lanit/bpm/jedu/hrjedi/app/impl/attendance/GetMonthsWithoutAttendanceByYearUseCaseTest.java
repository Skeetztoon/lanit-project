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

import org.apache.commons.collections4.SetUtils;
import org.junit.Assert;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GetMonthsWithoutAttendanceByYearUseCaseTest {
    @Mock
    private DateTimeUtils dateTimeUtils;
    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    protected GetMonthsWithoutAttendanceByYearUseCase getMonthsWithoutAttendanceByYearUseCase;

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_currentYear() {
        Mockito.when(dateTimeUtils.getCurrentMonth()).thenReturn(YearMonth.of(2020, Month.NOVEMBER));
        Mockito.when(attendanceRepository.findMonthsValuesWithAttendanceInfoByYear(2020)).thenReturn(SetUtils.hashSet(1, 3, 5, 7, 8, 9));

        List<YearMonth> monthsWithoutAttendanceInfo = getMonthsWithoutAttendanceByYearUseCase.execute(2020);

        Assert.assertEquals(Arrays.asList(
            YearMonth.of(2020, Month.FEBRUARY),
            YearMonth.of(2020, Month.APRIL),
            YearMonth.of(2020, Month.JUNE),
            YearMonth.of(2020, Month.OCTOBER)
        ), monthsWithoutAttendanceInfo);
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_currentYear_allRequiredMonthWithAttendanceInfo() {
        Mockito.when(dateTimeUtils.getCurrentMonth()).thenReturn(YearMonth.of(2020, Month.NOVEMBER));
        Mockito.when(attendanceRepository.findMonthsValuesWithAttendanceInfoByYear(2020)).thenReturn(SetUtils.hashSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        List<YearMonth> monthsWithoutAttendanceInfo = getMonthsWithoutAttendanceByYearUseCase.execute(2020);

        Assert.assertEquals(Collections.emptyList(), monthsWithoutAttendanceInfo);
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_futureYear() {
        Mockito.when(dateTimeUtils.getCurrentMonth()).thenReturn(YearMonth.of(2020, Month.NOVEMBER));

        List<YearMonth> monthsWithoutAttendanceInfo = getMonthsWithoutAttendanceByYearUseCase.execute(2021);

        Assert.assertEquals(Collections.emptyList(), monthsWithoutAttendanceInfo);
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_futureCurrentJanuary() {
        Mockito.when(dateTimeUtils.getCurrentMonth()).thenReturn(YearMonth.of(2020, Month.JANUARY));

        List<YearMonth> monthsWithoutAttendanceInfo = getMonthsWithoutAttendanceByYearUseCase.execute(2020);

        Assert.assertEquals(Collections.emptyList(), monthsWithoutAttendanceInfo);
    }

    @Test
    public void getMonthsWithoutAttendanceInfoByYear_forPastYear() {
        Mockito.when(dateTimeUtils.getCurrentMonth()).thenReturn(YearMonth.of(2021, Month.JANUARY));
        Mockito.when(attendanceRepository.findMonthsValuesWithAttendanceInfoByYear(2020)).thenReturn(SetUtils.hashSet(1, 3, 5, 7, 8, 9, 11));

        List<YearMonth> monthsWithoutAttendanceInfo = getMonthsWithoutAttendanceByYearUseCase.execute(2020);

        Assert.assertEquals(Arrays.asList(
            YearMonth.of(2020, Month.FEBRUARY),
            YearMonth.of(2020, Month.APRIL),
            YearMonth.of(2020, Month.JUNE),
            YearMonth.of(2020, Month.OCTOBER),
            YearMonth.of(2020, Month.DECEMBER)
        ), monthsWithoutAttendanceInfo);
    }
}
