package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentSlotDto {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private boolean available;
    private UserDto doctor;
    private DeviceDto device;
    private List<AppointmentDetailDto> appointmentDetailList;
}
