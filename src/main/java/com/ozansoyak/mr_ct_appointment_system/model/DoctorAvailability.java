package com.ozansoyak.mr_ct_appointment_system.model;

import com.ozansoyak.mr_ct_appointment_system.model.type.DayOfWeekType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeekType dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

}
