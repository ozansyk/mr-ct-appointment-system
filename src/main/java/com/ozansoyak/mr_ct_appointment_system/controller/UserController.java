package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.model.DoctorDetail;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.type.DoctorSpecialtyType;
import com.ozansoyak.mr_ct_appointment_system.model.type.UserType;
import com.ozansoyak.mr_ct_appointment_system.security.CustomUserDetails;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import com.ozansoyak.mr_ct_appointment_system.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.InvalidParameterException;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(
            UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, @RequestParam(required = false) DoctorSpecialtyType specialization, Model model) {
        // Kullanıcı adı kontrolü
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Bu kullanıcı adı zaten alınmış!");
            model.addAttribute("user", user);
            return "register";
        }

        if (user.getUserType() == UserType.DOCTOR) {
            if (specialization == null) {
                model.addAttribute("error", "Doktorlar için uzmanlık seçimi zorunludur!");
                model.addAttribute("user", user);
                return "register";
            }
            DoctorDetail doctorDetail = DoctorDetail.builder()
                    .specialty(specialization)
                    .build();
            user.setDoctorDetail(doctorDetail);
        }

        // Kullanıcıyı kaydet
        User savedUser = userService.registerUser(user);

        // Doğrulama sayfasına yönlendirme
        model.addAttribute("userEmail", savedUser.getEmail());
        return "verify";
    }


    @GetMapping("/verify")
    public String showVerificationForm() {
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam("verificationCode") Integer verificationCode, @RequestParam("email") String email, Model model) {
        Boolean isVerified = userService.activateUser(email, verificationCode);

        if (isVerified) {
            return "redirect:/login?verified";  // Doğrulandıktan sonra login sayfasına yönlendir
        } else {
            model.addAttribute("error", "Hatalı doğrulama kodu!");
            model.addAttribute("userEmail", email); // Kullanıcının mail adresini koruyoruz ki form tekrar doldurulabilsin
            return "verify";  // Yanlış kod girildiğinde aynı sayfada kal ve hata göster
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        String username = getUsername();
        User user = userService.findByUsername(username);
        addAttributesToModel(model, user);
        return "dashboard";
    }

    private void addAttributesToModel(Model model, User user) {
        model.addAttribute("userId", user.getId());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userType", user.getUserType().toString());
    }

    private static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping("/doctor-calendar")
    public String showDoctorCalendar(Model model) {
        String username = getUsername();
        User user = userService.findByUsername(username);
        if(!user.getUserType().equals(UserType.DOCTOR)) {
            return "redirect:/401";
        }
        addAttributesToModel(model, user);
        return "doctor-calendar";
    }

    @GetMapping("/admin-panel")
    public String showAdminPanel(Model model) {
        String username = getUsername();
        User user = userService.findByUsername(username);
        if(!user.getUserType().equals(UserType.ADMIN)) {
            return "redirect:/401";
        }
        addAttributesToModel(model, user);
        return "admin-panel";
    }

    @GetMapping("/401")
    public String show401() {
        return "401";
    }

}
