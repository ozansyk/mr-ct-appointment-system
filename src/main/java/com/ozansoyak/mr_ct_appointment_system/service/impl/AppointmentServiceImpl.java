package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.*;
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
                        .build(),
                AppointmentSlotDto.builder()
                        .id(3L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(10, 40))
                        .doctor(null)
                        .available(Boolean.TRUE)
                        .build());
    }

    @Override
    public ReserveAppointmentResponseDto reserveDoctorAppointment(ReserveDoctorAppointmentRequestDto request) {
        return ReserveAppointmentResponseDto.builder()
                .reservationCode("123asd")
                .build();
    }

    @Override
    public List<AppointmentSlotDto> getDeviceAvailability(Long deviceId) {
        return List.of( //TODO
                AppointmentSlotDto.builder()
                        .id(1L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(11, 0))
                        .doctor(null)
                        .available(Boolean.TRUE)
                        .build(),
                AppointmentSlotDto.builder()
                        .id(2L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(11, 20))
                        .doctor(null)
                        .available(Boolean.FALSE)
                        .build(),
                AppointmentSlotDto.builder()
                        .id(3L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(14, 0))
                        .doctor(null)
                        .available(Boolean.TRUE)
                        .build(),
                AppointmentSlotDto.builder()
                        .id(4L)
                        .date(LocalDate.now())
                        .time(LocalTime.of(14, 20))
                        .doctor(null)
                        .available(Boolean.TRUE)
                        .build());
    }

    @Override
    public ReserveAppointmentResponseDto reserveDeviceAppointment(ReserveDeviceAppointmentRequestDto request) {
        return ReserveAppointmentResponseDto.builder()
                .reservationCode("123asd")
                .build();
    }

    @Override
    public List<UserAppointmentResponseDto> getUserAppointmentList(Long id) {
        return List.of(
                UserAppointmentResponseDto.builder()
                        .id(1L)
                        .type("doctor")
                        .name("Dr. Şevket")
                        .date(LocalDate.now())
                        .time(LocalTime.of(15, 20))
                        .build(),
                UserAppointmentResponseDto.builder()
                        .id(2L)
                        .type("doctor")
                        .name("Dr. Anıl")
                        .date(LocalDate.now())
                        .time(LocalTime.of(16, 0))
                        .build(),
                UserAppointmentResponseDto.builder()
                        .id(3L)
                        .type("doctor")
                        .name("Dr. Ali")
                        .date(LocalDate.now().plusDays(1))
                        .time(LocalTime.of(11, 40))
                        .build());
    }

    @Override
    public void cancelAppointment(Long id) {
        //TODO
    }
}
