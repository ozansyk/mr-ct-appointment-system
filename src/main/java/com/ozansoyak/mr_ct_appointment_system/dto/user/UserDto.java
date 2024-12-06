package com.ozansoyak.mr_ct_appointment_system.dto.user;

import com.ozansoyak.mr_ct_appointment_system.model.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private UserType userType;
    private LocalDate birthDate;
    private AdminDetailDto adminDetailEntity;
    private PatientDetailDto patientDetailEntity;
    private DoctorDetailDto doctorDetail;
    private boolean enabled;

}
