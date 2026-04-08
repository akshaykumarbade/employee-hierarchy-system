package com.akshay.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {

    private Long employeeId;
    private String name;
    private String email;
    private String manager;
    private Long managerId;
    private String role;
    private UUID roleId;




}
