package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public List<UserDto> getUserInfoList() {
        return userService.findAll();
    }

    @PostMapping("/edit/{id}")
    public UserDto editUserInfo(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.editUserInfo(id, userDto);
    }

    @DeleteMapping("/ban/{id}")
    public ResponseEntity<Void> banUser(@PathVariable Long id) {
        userService.banUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/unban/{id}")
    public ResponseEntity<Void> unbanUser(@PathVariable Long id) {
        userService.unbanUser(id);
        return ResponseEntity.noContent().build();
    }
}
