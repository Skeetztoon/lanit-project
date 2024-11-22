package ru.lanit.bpm.jedu.hrjedi.adapter.persistence.employee.projection;

import java.util.Set;

public interface UserWithRolesProjection {
    String getUserCredentials();

    Set<String> getRoles();
}
