package com.ozansoyak.mr_ct_appointment_system.repository;

import com.ozansoyak.mr_ct_appointment_system.model.DoctorAvailability;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    @Query("FROM DoctorAvailability da " +
            "WHERE (da.doctor = :user) " +
            "AND (FUNCTION('DATE', da.startDateTime) = :date)")
    List<DoctorAvailability> findDoctorAvailabilityByDate(
            @Param("doctor")User user,
            @Param("date")LocalDate date);
}
