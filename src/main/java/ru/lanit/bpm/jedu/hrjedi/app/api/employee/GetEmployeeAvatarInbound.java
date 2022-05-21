package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import ru.lanit.bpm.jedu.hrjedi.domain.employee.EmployeeAvatar;

public interface GetEmployeeAvatarInbound {
    EmployeeAvatar execute(String userLogin);
}
