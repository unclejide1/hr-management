package com.example.hrmanagement.usecases.auth;

import com.example.hrmanagement.usecases.dto.request.SignUpRequest;


public interface SignUpUseCase {
    String createUser(SignUpRequest signUpRequest);
}
