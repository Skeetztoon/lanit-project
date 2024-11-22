package ru.lanit.bpm.jedu.hrjedi.domain.employee.projections;

import java.util.Set;

public interface UserWithRolesProjection {
    String getUserCredentials();

    Set<String> getRoles();
}
