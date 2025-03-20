package com.example.JobWebsite.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LogInRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
