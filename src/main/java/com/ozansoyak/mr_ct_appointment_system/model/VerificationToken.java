package com.ozansoyak.mr_ct_appointment_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer token;
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(User user, Integer verificationCode) {
        this.user = user;
        this.token = verificationCode;
    }

    public VerificationToken() {

    }
}
