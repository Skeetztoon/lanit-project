package ru.lanit.bpm.jedu.hrjedi.domain.employee;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class EmployeeAvatar {
    private Path avatarPath;
    private byte[] avatar;
}
