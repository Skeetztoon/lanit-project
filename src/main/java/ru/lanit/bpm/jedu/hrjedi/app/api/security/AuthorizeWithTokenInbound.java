package ru.lanit.bpm.jedu.hrjedi.app.api.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthorizeWithTokenInbound {
    UserDetails execute(String token);
}
