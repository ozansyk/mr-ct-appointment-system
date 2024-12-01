package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "doctor_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDetail extends AbstractEntity {

    private String phoneNumber;

    private String specialty;

    private String licenseNumber;

    private String clinicAddress;

}
