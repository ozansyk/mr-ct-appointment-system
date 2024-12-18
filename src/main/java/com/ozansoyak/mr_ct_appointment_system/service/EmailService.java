package com.ozansoyak.mr_ct_appointment_system.service;


import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentDto;

public interface EmailService {

    void sendVerificationEmail(String username, String toEmail, int verificationCode);

    void sendBookedReservationEmail(AppointmentDto appointmentDto);

    void cancelReservationEmail(AppointmentDto appointmentDto);

}
