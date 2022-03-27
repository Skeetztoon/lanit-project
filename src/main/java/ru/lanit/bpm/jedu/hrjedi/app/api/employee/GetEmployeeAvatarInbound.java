package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import java.nio.file.Path;

public interface GetEmployeeAvatarInbound {
    byte[] execute(Path path);
}
