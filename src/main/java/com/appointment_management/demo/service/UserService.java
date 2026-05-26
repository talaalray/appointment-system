package com.appointment_management.demo.service;

import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.repository.UserRepository;
import com.appointment_management.demo.security.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static UserRepository userRepo;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        userRepo= userRepository;
    }

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            log.info("make new user ");
            User u = new User();
            u.setUsername("admin");
            u.setPassword(passwordEncoder.encode("1234"));
            u.setRole(Role.ADMIN);
            User u1 = new User();
            u1.setUsername("customer");
            u1.setPassword(passwordEncoder.encode("1234"));
            u1.setRole(Role.CUSTOMER);
            User u2 = new User();
            u2.setUsername("staff");
            u2.setPassword(passwordEncoder.encode("1234"));
            u2.setRole(Role.STAFF);
            userRepository.save(u2);
            userRepository.save(u1);
            userRepository.save(u);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        System.out.println("user: " + identifier);
        return userRepository.findByUsername(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + identifier));
    }

    public User createUser(String username, String password, Role role){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return user;
    }

    public Map<String, Object> login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user is not found"));
        Map<String, Object> response = new HashMap<>();
        response.put("validate", true);

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("password is not correct");
        }
        String token = jwtService.generateAccessToken(user);
        response.put("token", token);
        response.put("user", user);
        return response;
    }

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;  // No authenticated user, return null
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        } else if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepo.findByUsername(username).orElse(null);
        } else if (principal instanceof String) {
            if (principal.equals("anonymousUser")){
                return null;
            }
            return userRepo.findByUsername((String)principal).orElse(null);
        }
        return null;

    }
}