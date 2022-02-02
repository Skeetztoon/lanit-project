package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

import java.util.Set;

public interface CreateEmployeeInbound {
    void execute(String login, String firstName, String patronymic, String lastName, String password, String email, Set<String> rolesStrings);
}
