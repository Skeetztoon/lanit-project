package ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsersJsonWrapper {
    private List<UserJsonDTO> users;
}
