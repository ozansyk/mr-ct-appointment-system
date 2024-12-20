package com.ozansoyak.mr_ct_appointment_system.dto.optimise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptimisedAppointmentDetail {

    private Long id;
    private String patientName;
    private String previousAppointmentDate;
    private String newAppointmentDate;

}
