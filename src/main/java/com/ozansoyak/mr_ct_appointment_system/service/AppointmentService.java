package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.*;

import java.util.List;

public interface AppointmentService {
    List<AppointmentSlotDto> getDoctorAvailability(Long doctorId);

    ReserveAppointmentResponseDto reserveDoctorAppointment(ReserveDoctorAppointmentRequestDto request);

    List<AppointmentSlotDto> getDeviceAvailability(Long deviceId);

    ReserveAppointmentResponseDto reserveDeviceAppointment(ReserveDeviceAppointmentRequestDto request);

    List<UserAppointmentResponseDto> getUserAppointmentList(Long id);

    void cancelAppointment(Long id);
}
