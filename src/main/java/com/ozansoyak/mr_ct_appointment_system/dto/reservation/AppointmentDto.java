package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {

    private Long id;
    private UserDto doctor;
    private DeviceDto device;
    private UserDto patient;
    private String reservationCode;
    private LocalDateTime appointmentStartDate;

}
