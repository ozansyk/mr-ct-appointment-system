package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "device")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private Boolean isActive;

}
