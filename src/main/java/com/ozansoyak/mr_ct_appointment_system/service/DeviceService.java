package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.dto.operation.OperationDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface DeviceService {
    List<DeviceDto> getDeviceList();

    void addDevice(DeviceDto deviceDto);

    void deleteDevice(Long id);

    DeviceDto getDevice(Long deviceId);

    //@Cacheable(value = "operations", key = "operations")
    List<OperationDto> getOperations();
}
