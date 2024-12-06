package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveAppointmentResponseDto {
    private String reservationCode;
}
