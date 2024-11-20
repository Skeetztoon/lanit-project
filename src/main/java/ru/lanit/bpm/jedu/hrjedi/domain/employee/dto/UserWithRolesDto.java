package ru.lanit.bpm.jedu.hrjedi.domain.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserWithRolesDto {
    private String login;
    private List<RoleDto> roles;
}
