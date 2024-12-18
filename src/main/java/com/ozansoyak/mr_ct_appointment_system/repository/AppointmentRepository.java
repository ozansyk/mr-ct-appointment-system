package com.ozansoyak.mr_ct_appointment_system.repository;

import com.ozansoyak.mr_ct_appointment_system.model.Appointment;
import com.ozansoyak.mr_ct_appointment_system.model.DeviceEntity;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.type.AppointmentStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("FROM Appointment a " +
            "WHERE (a.doctor = :doctor) " +
            "AND (FUNCTION('DATE', a.appointmentStartDate) = :date) " +
            "AND a.appointmentStatus IN :appointmentStatusTypeList")
    List<Appointment> findDoctorAppointmentsByDate(
            @Param("doctor")User doctor,
            @Param("date") LocalDate date,
            @Param("appointmentStatusTypeList")List<AppointmentStatusType> appointmentStatusTypeList);

    @Query("FROM Appointment a " +
            "WHERE (a.device = :device) " +
            "AND (FUNCTION('DATE', a.appointmentStartDate) = :date) " +
            "AND a.appointmentStatus IN :appointmentStatusTypeList")
    List<Appointment> findDeviceAppointmentsByDate(
            @Param("device") DeviceEntity device,
            @Param("date") LocalDate date,
            @Param("appointmentStatusTypeList")List<AppointmentStatusType> appointmentStatusTypeList);

    @Query("FROM Appointment a " +
            "WHERE(a.patient = :patient) " +
            "AND (a.appointmentStartDate > :date) " +
            "AND a.appointmentStatus IN :appointmentStatusTypeList")
    List<Appointment> findPatientActiveAppointments(
            @Param("patient") User patient,
            @Param("date") LocalDateTime date,
            @Param("appointmentStatusTypeList")List<AppointmentStatusType> appointmentStatusTypeList);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.doctor = :doctor " +
            "AND a.appointmentStartDate BETWEEN :startDate AND :endDate " +
            "AND a.appointmentStatus IN :appointmentStatusTypeList")
    boolean existsDoctorAppointmentsInDateRange(
            @Param("doctor") User doctor,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("appointmentStatusTypeList") List<AppointmentStatusType> appointmentStatusTypeList);

    @Query("FROM Appointment a " +
            "WHERE (a.doctor = :doctor) " +
            "AND a.appointmentStatus IN :appointmentStatusTypeList")
    List<Appointment> findActiveBookedDoctorAppointments(
            @Param("doctor") User doctor,
            @Param("appointmentStatusTypeList") List<AppointmentStatusType> appointmentStatusTypeList);

    List<Appointment> findByPatientAndAppointmentStatusIsNot(User patient, AppointmentStatusType appointmentStatus);

    @Query("FROM Appointment a " +
            "WHERE a.appointmentStartDate > :filterDate " +
            "AND a.appointmentStatus != :appointmentStatusType " +
            "ORDER BY a.appointmentStartDate ASC")
    List<Appointment> findAppointmentsForOptimiseSchedule(LocalDateTime filterDate, AppointmentStatusType appointmentStatusType);

}
