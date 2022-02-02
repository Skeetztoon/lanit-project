package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

public interface UpdateEmployeeEmailInbound {
    void execute(String login, String email);
}
