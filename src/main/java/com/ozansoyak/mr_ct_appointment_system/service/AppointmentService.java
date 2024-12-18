package com.ozansoyak.mr_ct_appointment_system.service;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.*;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    List<AppointmentSlotDto> getDoctorAvailability(Long doctorId, String date);

    ReserveAppointmentResponseDto reserveDoctorAppointment(ReserveDoctorAppointmentRequestDto request);

    List<AppointmentSlotDto> getDeviceAvailability(Long deviceId, String date);

    ReserveAppointmentResponseDto reserveDeviceAppointment(ReserveDeviceAppointmentRequestDto request);

    List<UserAppointmentResponseDto> getUserAppointmentList(Long id);

    void cancelAppointment(Long id);

    void createDoctorCalendar(CreateDoctorCalendarRequestDto request);

    List<DoctorCalendarResponseDto> findDoctorCalendarByDate(Long doctorId, LocalDate date);

    Boolean deleteDoctorCalendar(Long id);

    List<BookedDoctorAppointmentResponseDto> getBookedDoctorAppointments(Long id);

    SuggestReservationResponseDto getSuggestReservation(SuggestReservationRequestDto request);
}
