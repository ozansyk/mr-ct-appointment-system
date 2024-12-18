package com.ozansoyak.mr_ct_appointment_system.dto.optimise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptimiseAppointmentsResultDto {

    private Long totalFoundAppointment;
    private Long optimisedAppointmentCount;
    private List<Long> optimisedAppointmentIdList;

}
