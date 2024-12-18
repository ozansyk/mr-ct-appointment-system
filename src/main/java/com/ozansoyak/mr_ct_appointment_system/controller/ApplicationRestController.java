package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.optimise.OptimiseAppointmentsResultDto;
import com.ozansoyak.mr_ct_appointment_system.timer.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApplicationRestController {

    private final ScheduleService scheduleService;

    public ApplicationRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/optimise-appointments")
    public ResponseEntity<OptimiseAppointmentsResultDto> processOptimiseAppointments() {
        return ResponseEntity.ok(scheduleService.processOptimiseAppointments());
    }
}
