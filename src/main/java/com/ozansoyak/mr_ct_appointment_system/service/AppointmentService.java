package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentSlotDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.ReserveAppointmentRequestDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.ReserveAppointmentResponseDto;

import java.util.List;

public interface AppointmentService {
    List<AppointmentSlotDto> getDoctorAvailability(Long doctorId);

    ReserveAppointmentResponseDto reserveAppointment(ReserveAppointmentRequestDto request);
}
