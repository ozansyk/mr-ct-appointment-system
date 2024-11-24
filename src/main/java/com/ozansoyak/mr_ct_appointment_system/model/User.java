package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {

    private String username;
    private String email;
    private String password;
    private boolean enabled = false;  // Hesap aktif mi değil mi?
}
