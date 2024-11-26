package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UsersJsonWrapper;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.CreateMultipleEmployeesInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.LoadUsersFromJsonInbound;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadUsersFromJsonUseCase implements LoadUsersFromJsonInbound {
    private final CreateMultipleEmployeesInbound createMultipleEmployeesInbound;

    @Transactional
    @Override
    public boolean execute(UsersJsonWrapper usersWrapper) {
        log.info("Loading users");
        try {
            if (usersWrapper.getUsers() == null || usersWrapper.getUsers().isEmpty()) {
                log.info("Users not found in json");
                return false;
            }

            createMultipleEmployeesInbound.execute(usersWrapper.getUsers());
            log.info("Users loaded");
            return true;
        } catch (EmployeeRegistrationException e) {
            log.error("Error creating employee: {}", e.getMessage());
            return false;

        } catch (Exception e) {
            log.error("Error processing file: {}", e.getMessage());
            return false;
        }
    }
}
