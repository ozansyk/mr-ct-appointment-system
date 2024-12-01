package com.ozansoyak.mr_ct_appointment_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDetailDto {
    private String phoneNumber;
    private String specialty;
    private String licenseNumber;
    private String clinicAddress;
}
