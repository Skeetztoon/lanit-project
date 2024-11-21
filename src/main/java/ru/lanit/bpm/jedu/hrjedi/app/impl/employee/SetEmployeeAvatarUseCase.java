package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.SetEmployeeAvatarInbound;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SetEmployeeAvatarUseCase implements SetEmployeeAvatarInbound {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetEmployeeAvatarUseCase.class);

    @Override
    public boolean execute(MultipartFile imageFile, String userLogin) {
        try {
            Path filePath = Paths.get("target", "classes", "images", userLogin + ".png");
            Files.write(filePath, imageFile.getBytes());
            return true;
        } catch (IOException e) {
            LOGGER.error("Unable to save employee avatar. Message - {}", e.getMessage());
            return false;
        }
    }
}
