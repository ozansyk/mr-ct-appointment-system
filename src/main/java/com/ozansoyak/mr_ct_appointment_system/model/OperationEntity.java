package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "operation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationEntity extends AbstractEntity {

    private String operationName;

    private Long operationTime;

}
