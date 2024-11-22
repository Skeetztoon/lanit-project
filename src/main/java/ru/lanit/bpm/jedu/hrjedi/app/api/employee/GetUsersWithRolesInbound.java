package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.adapter.rest.employee.dto.UserWithRolesDto;

import java.util.List;

public interface GetUsersWithRolesInbound {
    List<UserWithRolesDto> execute();
}
