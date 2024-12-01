package com.ozansoyak.mr_ct_appointment_system.model;

import com.ozansoyak.mr_ct_appointment_system.model.type.AppointmentStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    private LocalDateTime appointmentStartDate;

    private LocalDateTime appointmentEndDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusType appointmentStatus;

}
