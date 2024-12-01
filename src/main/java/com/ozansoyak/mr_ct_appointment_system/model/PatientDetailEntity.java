package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patient_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailEntity extends AbstractEntity {

    private String phoneNumber;

    private String medicalHistory;

}
