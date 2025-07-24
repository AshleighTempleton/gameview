package com.projectash.gameview.services;

import org.springframework.stereotype.Service;

import com.projectash.gameview.entities.User;
import com.projectash.gameview.repositories.UserRepository;
import com.projectash.gameview.dtos.UserRegisterDto;


import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String registerUser(UserRegisterDto userRegisterDto) {
        if(userRepository.existsByUsername(userRegisterDto.getUsername())){
            return "Username already Exists";
        }
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            return "Email already registered";
        }
        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .enabled(true)
                .roles(Collections.singleton("USER"))
                .preferences(Collections.emptySet())
                .build();
        userRepository.save(user);
        return null;
    }
}

