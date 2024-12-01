package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.DoctorDetailDto;
import com.ozansoyak.mr_ct_appointment_system.dto.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.DoctorDetail;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.type.UserType;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private static final UserType DOCTOR = UserType.DOCTOR;

    private final UserRepository userRepository;

    public DoctorServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findByDoctorDetailSpecialty(String specialty) {
        return List.of(UserDto.builder()
                .id(1L)
                .username("Doktor1")
                .userType(UserType.DOCTOR)
                .doctorDetail(DoctorDetailDto.builder()
                        .phoneNumber("5555555555")
                        .specialty("Dahiliye")
                        .licenseNumber("123")
                        .clinicAddress("adres1")
                        .build())
                .enabled(Boolean.TRUE)
                .build());
        //return userRepository.findByUserTypeAndDoctorDetailSpecialty(DOCTOR, specialty); //TODO
    }
}
