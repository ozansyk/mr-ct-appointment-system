package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.dto.operation.OperationDto;
import com.ozansoyak.mr_ct_appointment_system.model.DeviceEntity;
import com.ozansoyak.mr_ct_appointment_system.model.OperationEntity;
import com.ozansoyak.mr_ct_appointment_system.repository.DeviceRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.OperationRepository;
import com.ozansoyak.mr_ct_appointment_system.service.DeviceService;
import com.ozansoyak.mr_ct_appointment_system.util.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl extends CommonService implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final OperationRepository operationRepository;

    public DeviceServiceImpl(
            ModelMapper modelMapper,
            DeviceRepository deviceRepository,
            OperationRepository operationRepository) {
        super(modelMapper);
        this.deviceRepository = deviceRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public List<DeviceDto> getDeviceList() {
        List<DeviceEntity> deviceEntities = deviceRepository.findByIsActiveTrueOrderByCreatedAtAsc();
        return deviceEntities.stream()
                .map(device -> map(device, DeviceDto.class))
                .toList();
    }

    @Override
    public void addDevice(DeviceDto deviceDto) {
        DeviceEntity deviceEntity = map(deviceDto, DeviceEntity.class);
        deviceEntity.getDeviceOperationEntities().forEach(deviceOperationEntity -> deviceOperationEntity.setDevice(deviceEntity));
        deviceEntity.setIsActive(Boolean.TRUE);
        deviceRepository.save(deviceEntity);
    }

    @Override
    public void deleteDevice(Long id) {
        DeviceEntity deviceEntity = deviceRepository.findById(id).orElseThrow();
        deviceEntity.setIsActive(false);
        deviceRepository.save(deviceEntity);
    }

    @Override
    public DeviceDto getDevice(Long deviceId) {
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElseThrow();
        return map(deviceEntity, DeviceDto.class);
    }

    @Override
    public List<OperationDto> getOperations() {
        List<OperationEntity> operationEntityList = operationRepository.findAll();
        return operationEntityList.stream()
                .map(operationEntity -> map(operationEntity, OperationDto.class))
                .toList();
    }
}
