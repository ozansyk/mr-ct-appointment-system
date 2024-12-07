package com.ozansoyak.mr_ct_appointment_system.dto.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDto {
    private Long id;
    private String name;
    private Boolean isActive;
}
