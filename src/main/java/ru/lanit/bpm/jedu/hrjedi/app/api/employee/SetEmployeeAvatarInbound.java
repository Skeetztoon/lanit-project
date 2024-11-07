package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import org.springframework.web.multipart.MultipartFile;

public interface SetEmployeeAvatarInbound {
    boolean execute(MultipartFile imageFile, String userLogin);
}
