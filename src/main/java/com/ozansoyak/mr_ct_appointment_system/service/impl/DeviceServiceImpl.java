package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.model.DeviceEntity;
import com.ozansoyak.mr_ct_appointment_system.repository.DeviceRepository;
import com.ozansoyak.mr_ct_appointment_system.service.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final ModelMapper modelMapper;

    public DeviceServiceImpl(
            DeviceRepository deviceRepository,
            ModelMapper modelMapper) {
        this.deviceRepository = deviceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DeviceDto> getDeviceList() {
        List<DeviceEntity> deviceEntities = deviceRepository.findByIsActiveTrueOrderByCreatedAtAsc();
        return deviceEntities.stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .toList();
    }

    @Override
    public void addDevice(DeviceDto deviceDto) {
        DeviceEntity deviceEntity = modelMapper.map(deviceDto, DeviceEntity.class);
        deviceEntity.setIsActive(Boolean.TRUE);
        deviceRepository.save(deviceEntity);
    }

    @Override
    public void deleteDevice(Long id) {
        DeviceEntity deviceEntity = deviceRepository.findById(id).orElseThrow();
        deviceEntity.setIsActive(false);
        deviceRepository.save(deviceEntity);
    }
}
