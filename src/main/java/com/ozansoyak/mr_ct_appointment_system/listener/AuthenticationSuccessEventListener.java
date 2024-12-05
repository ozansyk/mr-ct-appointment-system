package com.ozansoyak.mr_ct_appointment_system.listener;

import com.ozansoyak.mr_ct_appointment_system.model.ActionLogEntity;
import com.ozansoyak.mr_ct_appointment_system.model.type.ActionType;
import com.ozansoyak.mr_ct_appointment_system.repository.ActionLogRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final ActionLogRepository logRepository;

    public AuthenticationSuccessEventListener(ActionLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        saveLog(username);
    }

    private void saveLog(String username) {
        ActionLogEntity log = new ActionLogEntity();
        log.setUsername(username);
        log.setAction(ActionType.LOGIN);
        logRepository.save(log);
    }
}
