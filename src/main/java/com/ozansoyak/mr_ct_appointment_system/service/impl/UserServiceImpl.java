package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VerificationTokenRepository tokenRepository;

    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    private final EmailServiceImpl emailService;

    public UserServiceImpl(
            UserRepository userRepository,
            VerificationTokenRepository tokenRepository,
            JavaMailSender mailSender,
            PasswordEncoder passwordEncoder,
            EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User registerUser(User user) {
        // Kullanıcıyı kaydet
        // Şifreyi encode edip kaydediyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);  // Kullanıcı hesabı aktif değil olarak kaydedilir
        userRepository.save(user);

        // Doğrulama kodu oluştur
        int verificationCode = (int) (Math.random() * 9000) + 1000;

        // Doğrulama kodunu ve kullanıcıyı kaydet
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(3);
        VerificationToken token = new VerificationToken(verificationCode, expiryDate, user);
        tokenRepository.save(token);

        // Kullanıcıya doğrulama kodunu e-posta ile gönder
        emailService.sendVerificationEmail(user.getUsername(), user.getEmail(), verificationCode);
        return user;
    }

    @Override
    public void activateUser(User user) {
        user.setEnabled(true);  // Hesabı aktifleştir
        userRepository.save(user);
    }
}
