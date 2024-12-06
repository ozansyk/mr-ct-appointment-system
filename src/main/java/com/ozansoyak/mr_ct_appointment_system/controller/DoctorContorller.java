package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorContorller {

    private final DoctorService doctorService;

    public DoctorContorller(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getDoctorsBySpecialty(@RequestParam(value = "specialty") String specialty) {
        List<UserDto> doctors = doctorService.findByDoctorDetailSpecialty(specialty);
        return ResponseEntity.ok(doctors);
    }
}
