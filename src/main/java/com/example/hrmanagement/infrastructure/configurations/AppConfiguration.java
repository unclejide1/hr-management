package com.example.hrmanagement.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

/**
 * AppConfiguration
 */
@Configuration
public class AppConfiguration {



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        String SALT = "fhsjdhk12h3kkslkdsndwe"; //The encryption salt
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }
}