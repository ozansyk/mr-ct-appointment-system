package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentSlotDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.ReserveDeviceAppointmentRequestDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.ReserveDoctorAppointmentRequestDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.ReserveAppointmentResponseDto;
import com.ozansoyak.mr_ct_appointment_system.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<AppointmentSlotDto>> getDoctorSchedule(@RequestParam Long doctorId) {
        List<AppointmentSlotDto> slots = appointmentService.getDoctorAvailability(doctorId);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/reserve-doctor")
    public ResponseEntity<ReserveAppointmentResponseDto> reserveAppointment(@RequestBody ReserveDoctorAppointmentRequestDto request) {
        ReserveAppointmentResponseDto response = appointmentService.reserveDoctorAppointment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/device")
    public ResponseEntity<List<AppointmentSlotDto>> getDeviceSchedule(@RequestParam Long deviceId) {
        List<AppointmentSlotDto> slots = appointmentService.getDeviceAvailability(deviceId);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/reserve-device")
    public ResponseEntity<ReserveAppointmentResponseDto> reserveDevice(@RequestBody ReserveDeviceAppointmentRequestDto request) {
        ReserveAppointmentResponseDto response = appointmentService.reserveDeviceAppointment(request);
        return ResponseEntity.ok(response);
    }

}
