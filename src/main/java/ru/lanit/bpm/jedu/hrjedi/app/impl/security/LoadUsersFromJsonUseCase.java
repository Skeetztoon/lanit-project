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
    private static final String FIRST_NAME = "first-name";
    private static final String SECOND_NAME = "second-name";
    private static final String LAST_NAME = "last-name";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String ROLES = "roles";
    private final GenerateSecurePasswordInbound generateSecurePassword;
    private final CreateEmployeeInbound createEmployee;

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
        } catch (Exception e) {
            throw new EmployeeRegistrationException("Error processing file" + e.getMessage());
        }
        LOGGER.info("Users loaded");
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void checkCredentials(JsonNode user) {
        if ((!user.hasNonNull(FIRST_NAME) || user.get(FIRST_NAME).asText().isEmpty()) ||
            (!user.hasNonNull(LAST_NAME) || user.get(LAST_NAME).asText().isEmpty()) ||
            (!user.hasNonNull(USERNAME) || user.get(USERNAME).asText().isEmpty()) ||
            (!user.hasNonNull(EMAIL) || user.get(EMAIL).asText().isEmpty()) ||
            (!user.hasNonNull(ROLES) || !user.get(ROLES).isArray() || user.get(ROLES).isEmpty())
        ) {
            throw new EmployeeRegistrationException("Some required credentials are null");
        }
    }

    private void createUserFromNode(JsonNode user) {
        String login = user.get(USERNAME).asText();
        String firstName = user.get(FIRST_NAME).asText();
        String secondName = (user.has(SECOND_NAME)) ? user.get(SECOND_NAME).asText() : null;
        String lastName = user.get(LAST_NAME).asText();
        String email = user.get(EMAIL).asText();

        JsonNode roles = user.get(ROLES);
        Set<String> rolesSet = new HashSet<>();
        if (roles.isArray()) {
            for (JsonNode role : roles) {
                rolesSet.add(role.asText());
            }
        }

        String pass = generateSecurePassword.execute();
        createEmployee.execute(login, firstName, secondName, lastName, pass, email, rolesSet);
    }
}
