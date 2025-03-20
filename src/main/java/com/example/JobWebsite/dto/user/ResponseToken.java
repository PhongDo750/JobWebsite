package com.example.JobWebsite.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ResponseToken {
    private String accessTokenOP;
    private String accessTokenRP;
    private String role;
}
