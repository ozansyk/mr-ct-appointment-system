package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentDto;
import com.ozansoyak.mr_ct_appointment_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(String username, String toEmail, int verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("MR CT Hesap Doğrulama Kodu");
        message.setText("Kullanıcı adı: " + username + " Doğrulama kodunuz: " + verificationCode);
        mailSender.send(message);
    }

    @Override
    public void sendBookedReservationEmail(AppointmentDto appointmentDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appointmentDto.getPatient().getEmail());
        message.setSubject("MR CT Rezervasyonunuz Oluşturuldu");
        message.setText("Kullanıcı adı: " + appointmentDto.getPatient().getUsername() +
                "\nRezervasyon Tarihi: " + appointmentDto.getAppointmentStartDate().format(DATE_TIME_FORMATTER) +
                "\nRezervasyon Tipi: " + (Objects.nonNull(appointmentDto.getDoctor()) ? "Doktor" : "Cihaz") +
                "\nRezervasyon Kodu: " + appointmentDto.getReservationCode() +
                "\nRezervasyonunuz oluşturulmuştur.");
        mailSender.send(message);
    }

    @Override
    public void cancelReservationEmail(AppointmentDto appointmentDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appointmentDto.getPatient().getEmail());
        message.setSubject("MR CT Rezervasyonuz İptal Edildi");
        message.setText("Kullanıcı adı: " + appointmentDto.getPatient().getUsername() +
                "\nRezervasyon Tarihi: " + appointmentDto.getAppointmentStartDate().format(DATE_TIME_FORMATTER) +
                "\nRezervasyon Tipi: " + (Objects.nonNull(appointmentDto.getDoctor()) ? "Doktor" : "Cihaz") +
                "\nRezervasyon Kodu: " + appointmentDto.getReservationCode() +
                "\nRezervasyonunuz iptal edilmiştir.");
        mailSender.send(message);
    }
}
