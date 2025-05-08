package com.example.JobWebsite.dto.job;

import com.example.JobWebsite.dto.user.UserOutput;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JobOutputV2 {
    private Long id;
    private UserOutput userOutput;
    private String jobName;
    private Double minSalary;
    private Double maxSalary;
    private String address;
    private String province;
    private String expirationDate;
    private String occupationName;
    private String experience;
    private Integer headCount;
    private String jobType;
    private String jobLevel;
    private String educationLevel;
    private String descriptions;
    private String requiredJobList;
    private String employeeBenefitList;
    private Boolean hasLiked;
}
