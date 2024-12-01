package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.AppointmentSlotDto;
import com.ozansoyak.mr_ct_appointment_system.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentSlotDto>> getDoctorSchedule(@RequestParam Long doctorId) {
        List<AppointmentSlotDto> slots = appointmentService.getDoctorAvailability(doctorId);
        return ResponseEntity.ok(slots);
    }

}
