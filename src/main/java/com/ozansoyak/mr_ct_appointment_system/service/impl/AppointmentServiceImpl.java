package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.AppointmentSlotDto;
import com.ozansoyak.mr_ct_appointment_system.repository.AppointmentRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.DoctorAvailabilityRepository;
import com.ozansoyak.mr_ct_appointment_system.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            AppointmentRepository appointmentRepository) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentSlotDto> getDoctorAvailability(Long doctorId) {
        return List.of( //TODO
                AppointmentSlotDto.builder()
                        .id(1L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(10, 0))
                        .doctor(null)
                        .available(Boolean.TRUE)
                        .build(),
                AppointmentSlotDto.builder()
                        .id(2L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(10, 20))
                        .doctor(null)
                        .available(Boolean.FALSE)
                        .build());
    }
}
