package com.ozansoyak.mr_ct_appointment_system.listener;

import com.ozansoyak.mr_ct_appointment_system.model.ActionLogEntity;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.type.ActionType;
import com.ozansoyak.mr_ct_appointment_system.repository.ActionLogRepository;
import com.ozansoyak.mr_ct_appointment_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ActionLogRepository logRepository;

    private final UserService userService;

    public CustomLogoutSuccessHandler(
            ActionLogRepository logRepository,
            UserService userService) {
        this.logRepository = logRepository;
        this.userService = userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null && authentication.getName() != null) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            saveLog(user);
        }
        response.sendRedirect("/login?logout");
    }

    private void saveLog(User user) {
        ActionLogEntity log = new ActionLogEntity();
        log.setUser(user);
        log.setAction(ActionType.LOGOUT);
        logRepository.save(log);
    }
}
