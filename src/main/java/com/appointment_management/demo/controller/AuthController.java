package com.appointment_management.demo.controller;

import com.appointment_management.demo.dto.AuthDto;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto dto) {
        return ResponseEntity.ok(userService.login(dto.getUsername(), dto.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto dto) {
        return ResponseEntity.ok(userService.createUser(dto.getUsername(), dto.getPassword(), dto.getRole()));
    }
}