package com.ozansoyak.mr_ct_appointment_system.listener;

import com.ozansoyak.mr_ct_appointment_system.model.ActionLogEntity;
import com.ozansoyak.mr_ct_appointment_system.model.type.ActionType;
import com.ozansoyak.mr_ct_appointment_system.repository.ActionLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ActionLogRepository logRepository;

    public CustomLogoutSuccessHandler(ActionLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null && authentication.getName() != null) {
            String username = authentication.getName();
            saveLog(username);
        }
        response.sendRedirect("/login?logout");
    }

    private void saveLog(String username) {
        ActionLogEntity log = new ActionLogEntity();
        log.setUsername(username);
        log.setAction(ActionType.LOGOUT);
        logRepository.save(log);
    }
}
