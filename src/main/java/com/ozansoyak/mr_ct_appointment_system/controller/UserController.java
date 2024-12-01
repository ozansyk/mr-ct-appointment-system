package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import com.ozansoyak.mr_ct_appointment_system.security.CustomUserDetails;
import com.ozansoyak.mr_ct_appointment_system.service.impl.UserServiceImpl;
import com.ozansoyak.mr_ct_appointment_system.service.VerificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final VerificationService verificationService;

    public UserController(
            UserServiceImpl userService,
            VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Kullanıcıyı kaydet
        User savedUser = userService.registerUser(user);

        // Doğrulama sayfasına yönlendirme
        model.addAttribute("userEmail", savedUser.getEmail()); // Maili model'e ekleyelim ki verify sayfasında kullanılabilir olsun
        return "verify";  // Artık doğrulama sayfasına yönlendiriyoruz
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        return "dashboard";
    }
}
