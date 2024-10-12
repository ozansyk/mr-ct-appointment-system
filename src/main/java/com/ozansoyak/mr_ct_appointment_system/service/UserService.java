package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void registerUser(User user) {
        // Kullanıcıyı kaydet
        userRepository.save(user);

        // Doğrulama kodu oluştur
        int verificationCode = (int) (Math.random() * 9000) + 1000;

        // Doğrulama kodunu ve kullanıcıyı kaydet
        VerificationToken token = new VerificationToken(user, verificationCode);
        tokenRepository.save(token);

        // Kullanıcıya doğrulama kodunu e-posta ile gönder
        sendVerificationEmail(user.getEmail(), verificationCode);
    }

    private void sendVerificationEmail(String email, int verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        mailSender.send(message);
    }

    public boolean activateUser(Integer token) {
        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.isPresent() && verificationToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = verificationToken.get().getUser();
            user.setEnabled(true);  // Kullanıcıyı aktif hale getir
            userRepository.save(user);
            tokenRepository.delete(verificationToken.get());  // Token'ı sil
            return true;
        }
        return false;
    }
}
