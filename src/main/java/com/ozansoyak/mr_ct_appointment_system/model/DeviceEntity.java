package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private List<DeviceOperationEntity> deviceOperationEntities;

    private Boolean isActive;

}
