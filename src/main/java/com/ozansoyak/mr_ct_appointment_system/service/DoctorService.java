package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;

import java.util.List;

public interface DoctorService {
    List<UserDto> findByDoctorDetailSpecialty(String specialty);
}
