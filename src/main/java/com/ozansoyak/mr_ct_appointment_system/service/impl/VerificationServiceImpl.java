package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import com.ozansoyak.mr_ct_appointment_system.service.VerificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationServiceImpl implements VerificationService {

    private final VerificationTokenRepository tokenRepository;

    private final EmailServiceImpl emailService;

    private final UserServiceImpl userService;

    public VerificationServiceImpl(
            VerificationTokenRepository tokenRepository,
            EmailServiceImpl emailService,
            UserServiceImpl userService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.userService = userService;
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

    @Override
    public Boolean verifyCode(Integer verificationCode) {
        Optional<VerificationToken> token = tokenRepository.findByToken(verificationCode);
        if(token.isPresent()) {
            userService.activateUser(token.get().getUser());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
