package ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJsonDTO {
    @NotEmpty
    @JsonProperty("first-name")
    private String firstName;
    @NotEmpty
    @JsonProperty("second-name")
    private String secondName;
    @NotEmpty
    @JsonProperty("last-name")
    private String lastName;
    @NotEmpty
    @JsonProperty("username")
    private String username;
    @NotEmpty
    @JsonProperty("email")
    private String email;
    @JsonProperty("roles")
    @NotEmpty
    private List<String> roles;
}
