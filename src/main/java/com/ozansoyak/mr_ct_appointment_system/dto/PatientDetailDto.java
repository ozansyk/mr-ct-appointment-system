package com.ozansoyak.mr_ct_appointment_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDetailDto {
    private String phoneNumber;
    private String medicalHistory;
}
