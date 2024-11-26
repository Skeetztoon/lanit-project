package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UserJsonDTO;

import java.util.List;

public interface CreateMultipleEmployeesInbound {
    void execute(List<UserJsonDTO> users);
}
