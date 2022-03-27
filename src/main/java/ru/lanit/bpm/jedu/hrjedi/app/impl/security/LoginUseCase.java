package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.LoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.impl.security.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
public class LoginUseCase implements LoginInbound {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public String execute(String login, String password) {
        String trimmedLoginInLowerCase = login.trim().toLowerCase();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(trimmedLoginInLowerCase, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateJwtToken(authentication);
    }
}
