package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestReservationRequestDto {
    private String userId;
    private String appointmentType;
    private String deviceOrDoctorId;
    private LocalDate appointmentDate;
    private UrgencyType urgency;
}
