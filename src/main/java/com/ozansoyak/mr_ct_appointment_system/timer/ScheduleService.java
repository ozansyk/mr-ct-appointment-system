package com.ozansoyak.mr_ct_appointment_system.timer;

import com.ozansoyak.mr_ct_appointment_system.dto.optimise.OptimiseAppointmentsResultDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentDto;
import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentSlotDto;
import com.ozansoyak.mr_ct_appointment_system.model.Appointment;
import com.ozansoyak.mr_ct_appointment_system.model.type.AppointmentStatusType;
import com.ozansoyak.mr_ct_appointment_system.repository.AppointmentRepository;
import com.ozansoyak.mr_ct_appointment_system.service.AppointmentService;
import com.ozansoyak.mr_ct_appointment_system.service.EmailService;
import com.ozansoyak.mr_ct_appointment_system.util.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class ScheduleService extends CommonService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final AppointmentRepository appointmentRepository;

    private final AppointmentService appointmentService;

    private final EmailService emailService;

    public ScheduleService(
            ModelMapper modelMapper,
            AppointmentRepository appointmentRepository,
            AppointmentService appointmentService,
            EmailService emailService) {
        super(modelMapper);
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void optimiseAppointments() {
        log.info("#optimiseAppointments Scheduling started.");
        //OptimiseAppointmentsResultDto optimiseAppointmentsResultDto = processOptimiseAppointments();
        //log.info("#optimiseAppointments results: {}", optimiseAppointmentsResultDto.toString());
        log.info("#optimiseAppointments Scheduling finished.");
    }

    public OptimiseAppointmentsResultDto processOptimiseAppointments() {
        LocalDateTime filterDate = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
        List<Appointment> allFilteredAppointments = appointmentRepository.findAppointmentsForOptimiseSchedule(filterDate, AppointmentStatusType.CANCELLED);
        List<AppointmentDto> allFilteredAppointmentDtos = allFilteredAppointments.stream()
                .map(appointment -> map(appointment, AppointmentDto.class))
                .toList();
        List<Long> optimisedAppointmentIdList = new ArrayList<>();
        allFilteredAppointmentDtos.forEach(appointment -> {
            LocalDate date = filterDate.toLocalDate();
            LocalDateTime oldAppointmentDate = appointment.getAppointmentStartDate();
            List<AppointmentSlotDto> appointmentSlotDtoList = null;
            if(Objects.nonNull(appointment.getDoctor())) {
                for(int i=0; i<5; i++) {
                    appointmentSlotDtoList = appointmentService
                            .getDoctorAvailability(appointment.getDoctor().getId(), date.format(DATE_TIME_FORMATTER));
                    if(!appointmentSlotDtoList.isEmpty()) {
                        break;
                    }
                    date = date.plusDays(1);
                }
            } else {
                for(int i=0; i<5; i++) {
                    appointmentSlotDtoList = appointmentService
                            .getDeviceAvailability(appointment.getDevice().getId(), date.format(DATE_TIME_FORMATTER));
                    if(!appointmentSlotDtoList.isEmpty()) {
                        break;
                    }
                    date = date.plusDays(1);
                }
            }
            appointment.setAppointmentStartDate(LocalDateTime.of(appointmentSlotDtoList.get(0).getDate(), appointmentSlotDtoList.get(0).getTime()));
            appointmentRepository.save(map(appointment, Appointment.class));
            emailService.sendOptimisedReservationEmail(appointment, oldAppointmentDate);
            optimisedAppointmentIdList.add(appointment.getId());
        });
        return OptimiseAppointmentsResultDto.builder()
                .totalFoundAppointment((long) allFilteredAppointments.size())
                .optimisedAppointmentCount((long) optimisedAppointmentIdList.size())
                .optimisedAppointmentIdList(optimisedAppointmentIdList)
                .build();
    }
}
