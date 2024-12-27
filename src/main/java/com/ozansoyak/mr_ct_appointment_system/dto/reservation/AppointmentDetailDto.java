package com.ozansoyak.mr_ct_appointment_system.dto.reservation;

import com.ozansoyak.mr_ct_appointment_system.dto.operation.OperationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDetailDto {

    private String appointmentTime;
    private OperationDto operation;

}
