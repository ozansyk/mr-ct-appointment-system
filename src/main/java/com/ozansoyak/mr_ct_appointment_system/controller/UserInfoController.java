package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/edit/{id}")
    public UserDto editUserInfo(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.editUserInfo(id, userDto);
    }
}
