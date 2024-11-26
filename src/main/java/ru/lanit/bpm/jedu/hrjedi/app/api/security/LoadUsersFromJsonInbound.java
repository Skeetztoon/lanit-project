package ru.lanit.bpm.jedu.hrjedi.app.api.security;

import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.UsersJsonWrapper;

public interface LoadUsersFromJsonInbound {
    boolean execute(UsersJsonWrapper usersWrapper);
}
