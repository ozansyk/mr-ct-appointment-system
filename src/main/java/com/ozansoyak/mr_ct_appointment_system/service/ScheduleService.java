package com.ozansoyak.mr_ct_appointment_system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleService {

    private final AppointmentService appointmentService;

    public ScheduleService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Scheduled(cron = "30 0/5 * * * ?")
    public void schedule() {
        log.info("Scheduling started.");
        appointmentService.getDeviceAvailability(1L);
        log.info("Scheduling finished.");
    }
}
