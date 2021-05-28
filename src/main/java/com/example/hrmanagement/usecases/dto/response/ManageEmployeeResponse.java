package com.example.hrmanagement.usecases.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageEmployeeResponse {

    private String username;

    private Set<String> roles;

    private String dept;

}
