package com.ozansoyak.mr_ct_appointment_system.model;

import com.ozansoyak.mr_ct_appointment_system.model.type.DoctorSpecialtyType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private DoctorSpecialtyType specialty;

    private String phoneNumber;

    private String licenseNumber;

    private String clinicAddress;

}
