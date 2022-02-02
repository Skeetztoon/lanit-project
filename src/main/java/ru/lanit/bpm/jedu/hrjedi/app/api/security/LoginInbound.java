package ru.lanit.bpm.jedu.hrjedi.app.api.security;

public interface LoginInbound {
    String execute(String login, String password);
}
