package com.ozansoyak.mr_ct_appointment_system.config;

import java.util.TimeZone;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Istanbul"));
    }
}
