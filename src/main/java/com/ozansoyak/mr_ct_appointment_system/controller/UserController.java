package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import com.ozansoyak.mr_ct_appointment_system.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Şifreyi encode edip kaydediyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);  // Kullanıcı hesabı aktif değil olarak kaydedilir
        userRepository.save(user);

        // Doğrulama kodu oluşturuluyor
        int verificationCode = (int) (Math.random() * 9000) + 1000;
        VerificationToken token = new VerificationToken(user, verificationCode);
        tokenRepository.save(token);

        // Doğrulama kodu mail ile gönderiliyor
        try {
            emailService.sendVerificationEmail(user.getUsername(), user.getEmail(), verificationCode);
        } catch (Exception e) {
            System.out.println("userName: " + user.getUsername() + " verificationCode: " + verificationCode);
        }

        // Doğrulama sayfasına yönlendirme
        model.addAttribute("userEmail", user.getEmail()); // Maili model'e ekleyelim ki verify sayfasında kullanılabilir olsun
        return "verify";  // Artık doğrulama sayfasına yönlendiriyoruz
    }

    @GetMapping("/verify")
    public String showVerificationForm() {
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam("verificationCode") Integer verificationCode, @RequestParam("email") String email, Model model) {
        Optional<VerificationToken> token = tokenRepository.findByToken(verificationCode);

        if (token.isPresent()) {
            User user = token.get().getUser();
            user.setEnabled(true);  // Hesabı aktifleştir
            userRepository.save(user);
            return "redirect:/login?verified";  // Doğrulandıktan sonra login sayfasına yönlendir
        } else {
            model.addAttribute("error", "Invalid verification code");
            model.addAttribute("userEmail", email); // Kullanıcının mail adresini koruyoruz ki form tekrar doldurulabilsin
            return "verify";  // Yanlış kod girildiğinde aynı sayfada kal ve hata göster
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";  // Login sayfasını döner
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";  // Giriş sonrası gösterilecek sayfa
    }
}
