package com.appointment_management.demo.controller;

import com.appointment_management.demo.entity.Appointment;
import com.appointment_management.demo.entity.User;
import com.appointment_management.demo.enums.Role;
import com.appointment_management.demo.service.AppointmentService;
import com.appointment_management.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final PasswordEncoder passwordEncoder;

    public WebController(UserService userService, AppointmentService appointmentService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/web/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/web/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            User user = (User) userService.loadUserByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("user", user);
                return "redirect:/dashboard";
            }
        } catch (Exception e) {
            // ignored
        }
        model.addAttribute("error", "اسم المستخدم أو كلمة المرور غير صحيحة");
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/web/login";
        }

        List<Appointment> appointments = appointmentService.getForCustomer(user.getId());
        long upcomingCount = appointments.stream()
                .filter(a -> a.getStatus().name().equals("PENDING") || a.getStatus().name().equals("APPROVED"))
                .count();

        model.addAttribute("user", user);
        model.addAttribute("appointments", appointments);
        model.addAttribute("upcomingCount", upcomingCount);

        if (user.getRole() == Role.ADMIN) {
            return "dashboard/admin";
        } else if (user.getRole() == Role.STAFF) {
            return "dashboard/staff";
        } else {
            return "dashboard/customer";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/web/login";
    }
}
