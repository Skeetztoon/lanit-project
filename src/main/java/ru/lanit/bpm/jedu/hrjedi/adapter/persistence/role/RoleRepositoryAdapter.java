package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.RoleRepository;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleJpaRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll();
    }
}
