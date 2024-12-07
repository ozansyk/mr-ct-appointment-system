package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.VerificationTokenRepository;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import com.ozansoyak.mr_ct_appointment_system.service.VerificationService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VerificationTokenRepository tokenRepository;

    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    private final EmailServiceImpl emailService;

    private final VerificationService verificationService;

    private final ModelMapper modelMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            VerificationTokenRepository tokenRepository,
            JavaMailSender mailSender,
            PasswordEncoder passwordEncoder,
            EmailServiceImpl emailService,
            VerificationService verificationService,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationService = verificationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public User registerUser(User user) {
        // Kullanıcıyı kaydet
        // Şifreyi encode edip kaydediyoruz
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);  // Kullanıcı hesabı aktif değil olarak kaydedilir
        userRepository.save(user);

        verificationService.generateVerificationCodeAndSend(user);
        return user;
    }

    @Override
    public Boolean activateUser(String email, Integer verificationCode) {
        Optional<VerificationToken> token = tokenRepository.findByTokenAndUser_Email(verificationCode, email);
        if(token.isPresent()) {
            User user = token.get().getUser();
            user.setEnabled(true);  // Hesabı aktifleştir
            userRepository.save(user);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean usernameExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).get();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto editUserInfo(Long id, UserDto userDto) {
        User user = userRepository.findById(id).get();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());
        User userUpdated = userRepository.save(user);
        return modelMapper.map(userUpdated, UserDto.class);
    }
}
