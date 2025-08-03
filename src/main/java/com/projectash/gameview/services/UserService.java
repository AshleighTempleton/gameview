package com.projectash.gameview.services;

import org.springframework.stereotype.Service;

import com.projectash.gameview.entities.User;
import com.projectash.gameview.repositories.UserRepository;
import com.projectash.gameview.dtos.UserRegisterDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return "Success";
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
            .map(User::getId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // .getId();
    }

    public String getUsernameByid(Long userId){
        return userRepository.findById(userId)
            .map(User::getUsername)
            .orElse("Username not found");
    }
}

