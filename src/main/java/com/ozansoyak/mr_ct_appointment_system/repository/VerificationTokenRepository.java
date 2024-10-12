package com.ozansoyak.mr_ct_appointment_system.repository;

import com.ozansoyak.mr_ct_appointment_system.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(Integer token);
}
