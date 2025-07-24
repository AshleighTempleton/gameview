package com.projectash.gameview.controllers;

import lombok.RequiredArgsConstructor;

import com.projectash.gameview.dtos.UserRegisterDto;
import com.projectash.gameview.services.UserService;

import org.springframework.ui.Model;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequiredArgsConstructor
public class RegistrationController {
private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegisterDto userRegisterDto, Model model) {
        System.out.println("Registering user: " + userRegisterDto.getUsername());
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }
        String registrationError = userService.registerUser(userRegisterDto);
        if (registrationError != null) {
            model.addAttribute("error", registrationError);
            return "register";
        }
        return "loginToGame";
    }

}
