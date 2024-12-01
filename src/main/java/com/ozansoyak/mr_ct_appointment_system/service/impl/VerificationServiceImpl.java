package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import com.ozansoyak.mr_ct_appointment_system.service.EmailService;
import com.ozansoyak.mr_ct_appointment_system.service.VerificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationServiceImpl implements VerificationService {

    private final VerificationTokenRepository tokenRepository;

    private final EmailService emailService;

    public VerificationServiceImpl(
            VerificationTokenRepository tokenRepository,
            EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public void generateVerificationCodeAndSend(User user) {
        // Doğrulama kodu oluşturuluyor
        int verificationCode = (int) (Math.random() * 9000) + 1000;
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(3);
        VerificationToken token = new VerificationToken(verificationCode, expiryDate, user);
        tokenRepository.save(token);

        // Doğrulama kodu mail ile gönderiliyor
        try {
            emailService.sendVerificationEmail(user.getUsername(), user.getEmail(), verificationCode);
        } catch (Exception e) {
            System.out.println("userName: " + user.getUsername() + " verificationCode: " + verificationCode);
        }
    }
}
