package com.ozansoyak.mr_ct_appointment_system.dto.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDto {
    private Long id;
    private String name;
    private Boolean isActive;
    private List<DeviceOperationResponseDto> deviceOperationEntities;
}
