package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.model.User;

public interface VerificationService {

    void generateVerificationCodeAndSend(User user);

}
