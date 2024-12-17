package com.ozansoyak.mr_ct_appointment_system.model;

import com.ozansoyak.mr_ct_appointment_system.model.type.AppointmentStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    private String reservationCode;

    private LocalDateTime appointmentStartDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusType appointmentStatus;

}
