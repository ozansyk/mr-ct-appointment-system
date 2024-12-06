package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Override
    public List<Map<String, Object>> getDeviceList() {
        return List.of(Map.of("id", 1L, "name", "MR"),
                Map.of("id", 2L, "name", "CT"),
                Map.of("id", 3L, "name", "Röntgen"));
    }
}
