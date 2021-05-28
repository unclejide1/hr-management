package com.example.hrmanagement.usecases.auth;


import com.example.hrmanagement.usecases.dto.request.LoginRequest;
import com.example.hrmanagement.usecases.dto.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse processLogin(LoginRequest loginRequest);
}
