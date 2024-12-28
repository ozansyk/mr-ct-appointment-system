package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.dto.operation.OperationDto;
import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.type.AppointmentStatusType;
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
    private OperationDto operation;
    private UserDto patient;
    private String reservationCode;
    private UrgencyType urgency;
    private LocalDateTime appointmentStartDate;
    private AppointmentStatusType appointmentStatus;

}
