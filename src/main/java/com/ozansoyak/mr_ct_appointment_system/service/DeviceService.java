package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;

import java.util.List;

public interface DeviceService {
    List<DeviceDto> getDeviceList();

    void addDevice(DeviceDto deviceDto);

    void deleteDevice(Long id);
}
