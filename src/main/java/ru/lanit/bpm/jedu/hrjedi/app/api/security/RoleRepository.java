package ru.lanit.bpm.jedu.hrjedi.app.api.security;

import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(RoleName name);
}
