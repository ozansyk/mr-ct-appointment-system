package com.ozansoyak.mr_ct_appointment_system.model;

import com.ozansoyak.mr_ct_appointment_system.model.type.ActionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "login_logout_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionLogEntity extends AbstractEntity {

    private String username;

    @Enumerated(EnumType.STRING)
    private ActionType action;

}
