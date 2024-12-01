package com.ozansoyak.mr_ct_appointment_system.repository;

import com.ozansoyak.mr_ct_appointment_system.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
}
