package com.example.hrmanagement.usecases.dto.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String username;
    private String jwt;
    private List<String> roles;
}
