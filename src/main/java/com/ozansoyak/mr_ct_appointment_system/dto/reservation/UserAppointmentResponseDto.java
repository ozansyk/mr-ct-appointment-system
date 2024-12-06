package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAppointmentResponseDto {

    private Long id;
    private String type;
    private String name;
    private LocalDate date;
    private LocalTime time;

}
