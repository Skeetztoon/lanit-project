package ru.lanit.bpm.jedu.hrjedi.domain.employee;

import lombok.Data;

import java.nio.file.Path;

@Data
public class EmployeeAvatar {
    private Path avatarPath;
    private byte[] avatar;
}
