package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    User registerUser(User user);

    Boolean activateUser(String email, Integer verificationCode);

    boolean usernameExists(String username);

    User findByUsername(String username);

    UserDto findUserById(Long id);

    UserDto editUserInfo(Long id, UserDto userDto);
}
