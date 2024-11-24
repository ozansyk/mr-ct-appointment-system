package com.ozansoyak.mr_ct_appointment_system.service;

public interface EmailService {

    void sendVerificationEmail(String username, String toEmail, int verificationCode);

}
