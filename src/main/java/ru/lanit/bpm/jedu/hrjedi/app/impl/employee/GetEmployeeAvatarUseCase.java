package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetEmployeeAvatarInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.EmployeeAvatar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class GetEmployeeAvatarUseCase implements GetEmployeeAvatarInbound {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetEmployeeAvatarUseCase.class);

    @Override
    public EmployeeAvatar execute(String userLogin) {
        EmployeeAvatar employeeAvatar = new EmployeeAvatar();
        employeeAvatar.setAvatarPath(Paths.get("target", "classes", "images", userLogin + ".png"));
        try {
            employeeAvatar.setAvatar(Files.readAllBytes(employeeAvatar.getAvatarPath()));
            return employeeAvatar;
        } catch (IOException e) {
            LOGGER.error("Unable to get employee avatar. Message - {}", e.getMessage());
            return null;
        }
    }
}
