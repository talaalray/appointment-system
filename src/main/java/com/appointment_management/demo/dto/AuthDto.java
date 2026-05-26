package com.appointment_management.demo.dto;

import com.appointment_management.demo.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class AuthDto {
    private String username;
    private String password;
    private Role role;
}
