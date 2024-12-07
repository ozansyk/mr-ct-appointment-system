package com.ozansoyak.mr_ct_appointment_system.listener;

import com.ozansoyak.mr_ct_appointment_system.model.ActionLogEntity;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.type.ActionType;
import com.ozansoyak.mr_ct_appointment_system.repository.ActionLogRepository;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final ActionLogRepository logRepository;

    private final UserService userService;

    public AuthenticationSuccessEventListener(
            ActionLogRepository logRepository,
            UserService userService) {
        this.logRepository = logRepository;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        User user = userService.findByUsername(username);
        saveLog(user);
    }

    private void saveLog(User user) {
        ActionLogEntity log = new ActionLogEntity();
        log.setUser(user);
        log.setAction(ActionType.LOGIN);
        logRepository.save(log);
    }
}
