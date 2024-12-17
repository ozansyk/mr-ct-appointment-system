package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_availability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAvailability extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

}
