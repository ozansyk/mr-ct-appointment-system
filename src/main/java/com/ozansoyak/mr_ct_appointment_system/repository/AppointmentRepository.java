package com.ozansoyak.mr_ct_appointment_system.repository;

import com.ozansoyak.mr_ct_appointment_system.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
