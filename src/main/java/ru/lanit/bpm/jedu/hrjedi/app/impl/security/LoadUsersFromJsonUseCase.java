package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.CreateEmployeeInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.LoadUsersFromJsonInbound;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LoadUsersFromJsonUseCase implements LoadUsersFromJsonInbound {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadUsersFromJsonUseCase.class);
    private GenerateSecurePasswordInbound generateSecurePasswordInbound;
    private CreateEmployeeInbound createEmployeeInbound;

    @Transactional
    @Override
    public void execute(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode users = mapper.readTree(json).get("users");
            if (users.isArray()) {
                for (JsonNode user : users) {
                    checkCredentials(user);
                    createUserFromNode(user);
                }
            }
        } catch (EmployeeRegistrationException e) {
            throw e;
        } catch (Exception e) {
            throw new EmployeeRegistrationException("Error processing file" + e.getMessage());
        }
        LOGGER.info("Users loaded");
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void checkCredentials(JsonNode user) {
        if ((!user.hasNonNull("first-name")) ||
            (!user.hasNonNull("last-name")) ||
            (!user.hasNonNull("username")) ||
            (!user.hasNonNull("email")) ||
            (!user.hasNonNull("roles"))
        ) {
            throw new EmployeeRegistrationException("Some required credentials are null");
        }
    }

    private void createUserFromNode(JsonNode user) {
        String login = user.get("username").asText();
        String firstName = user.get("first-name").asText();
        String secondName = (user.get("second-name").asText() != null) ? user.get("second-name").asText() : null;
        String lastName = user.get("last-name").asText();
        String email = user.get("email").asText();

        JsonNode roles = user.get("roles");
        Set<String> rolesSet = new HashSet<>();
        if (roles.isArray()) {
            for (JsonNode role : roles) {
                rolesSet.add(role.asText());
            }
        }

        String pass = generateSecurePasswordInbound.execute();

        createEmployeeInbound.execute(login, firstName, secondName, lastName, pass, email, rolesSet);
    }
}
