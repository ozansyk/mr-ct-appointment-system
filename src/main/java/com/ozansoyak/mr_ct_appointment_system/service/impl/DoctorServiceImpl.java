package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.user.DoctorDetailDto;
import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.type.DoctorSpecialtyType;
import com.ozansoyak.mr_ct_appointment_system.model.type.UserType;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.service.DoctorService;
import com.ozansoyak.mr_ct_appointment_system.util.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DoctorServiceImpl extends CommonService implements DoctorService {

    private static final UserType DOCTOR = UserType.DOCTOR;

    private final UserRepository userRepository;

    public DoctorServiceImpl(
            ModelMapper modelMapper,
            UserRepository userRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findByDoctorDetailSpecialty(String specialty) {
        DoctorSpecialtyType doctorSpecialty = DoctorSpecialtyType.valueOf(specialty);
        return userRepository.findByUserTypeAndDoctorDetailSpecialty(DOCTOR, doctorSpecialty).stream()
                .map(user -> map(user, UserDto.class))
                .toList();
    }

    @Override
    public List<String> getSpecialtyList() {
        return Arrays.stream(DoctorSpecialtyType.values())
                .map(DoctorSpecialtyType::toString)
                .toList();
    }
}
