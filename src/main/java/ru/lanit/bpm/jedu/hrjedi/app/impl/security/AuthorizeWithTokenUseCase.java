package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.AuthorizeWithTokenInbound;
import ru.lanit.bpm.jedu.hrjedi.app.impl.security.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
public class AuthorizeWithTokenUseCase implements AuthorizeWithTokenInbound {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final IdentityService camundaIdentityService;

    @Transactional
    @Override
    public UserDetails execute(String token) {
        if (jwtProvider.validateJwtToken(token)) {
            String username = jwtProvider.getUserNameFromJwtToken(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            Authentication camundaAuthentication = new Authentication(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
            );
            camundaIdentityService.setAuthentication(camundaAuthentication);

            return userDetails;
        } else {
            return null;
        }
    }
}
