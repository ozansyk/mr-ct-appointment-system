package com.ozansoyak.mr_ct_appointment_system.dto.device;

import com.ozansoyak.mr_ct_appointment_system.dto.operation.OperationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceOperationDto {
    private Long id;
    private DeviceDto device;
    private OperationDto operation;
}
