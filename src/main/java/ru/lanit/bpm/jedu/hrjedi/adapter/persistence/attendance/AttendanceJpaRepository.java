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
package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lanit.bpm.jedu.hrjedi.domain.attendance.Attendance;

import java.util.List;
import java.util.Set;

public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findAllByEmployeeId(Long id);

    @Query("select distinct month(a.entranceTime)  as monthValue from Attendance a where year(a.entranceTime)=:year")
    Set<Integer> findMonthsValuesWithAttendanceInfoByYear(@Param("year") int year);

    @Query("select a from Attendance a where year(a.entranceTime) = :year and month(a.entranceTime) = :month")
    List<Attendance> findAllByMonth(@Param("year") int year, @Param("month") int month);
}
