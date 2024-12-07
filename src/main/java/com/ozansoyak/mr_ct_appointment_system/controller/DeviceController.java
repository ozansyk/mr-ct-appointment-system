package com.ozansoyak.mr_ct_appointment_system.controller;

import com.ozansoyak.mr_ct_appointment_system.dto.device.DeviceDto;
import com.ozansoyak.mr_ct_appointment_system.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/list")
    public List<DeviceDto> getDeviceList() {
        return deviceService.getDeviceList();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addDevice(@RequestBody DeviceDto deviceDto) {
        deviceService.addDevice(deviceDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();
    }

}
