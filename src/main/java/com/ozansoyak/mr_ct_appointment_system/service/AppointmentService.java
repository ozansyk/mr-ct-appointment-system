package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.AppointmentSlotDto;

import java.util.List;

public interface AppointmentService {
    List<AppointmentSlotDto> getDoctorAvailability(Long doctorId);
}
