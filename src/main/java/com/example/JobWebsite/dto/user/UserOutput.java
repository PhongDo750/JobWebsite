package com.example.JobWebsite.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutput {
    private Long id;
    private String fullName;
    private String imageUrl;
}
