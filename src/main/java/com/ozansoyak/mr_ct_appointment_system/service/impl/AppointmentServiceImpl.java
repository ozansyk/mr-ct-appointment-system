package com.ozansoyak.mr_ct_appointment_system.service.impl;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.*;
import com.ozansoyak.mr_ct_appointment_system.dto.user.UserDto;
import com.ozansoyak.mr_ct_appointment_system.model.Appointment;
import com.ozansoyak.mr_ct_appointment_system.model.DeviceEntity;
import com.ozansoyak.mr_ct_appointment_system.model.DoctorAvailability;
import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.model.type.AppointmentStatusType;
import com.ozansoyak.mr_ct_appointment_system.repository.AppointmentRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.DeviceRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.DoctorAvailabilityRepository;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.service.AppointmentService;
import com.ozansoyak.mr_ct_appointment_system.service.EmailService;
import com.ozansoyak.mr_ct_appointment_system.util.CommonService;
import com.ozansoyak.mr_ct_appointment_system.util.ReservationCodeGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl extends CommonService implements AppointmentService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final List<AppointmentStatusType> bookedAppointmentStatusTypeList = List.of(AppointmentStatusType.PENDING, AppointmentStatusType.CONFIRMED);

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    private final AppointmentRepository appointmentRepository;

    private final DeviceRepository deviceRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    public AppointmentServiceImpl(
            ModelMapper modelMapper,
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            AppointmentRepository appointmentRepository,
            DeviceRepository deviceRepository,
            UserRepository userRepository,
            EmailService emailService) {
        super(modelMapper);
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.appointmentRepository = appointmentRepository;
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public List<AppointmentSlotDto> getDoctorAvailability(Long doctorId, String dateString) {
        User doctor = userRepository.findById(doctorId).get();
        LocalDate date = LocalDate.parse(dateString, DATE_TIME_FORMATTER);
        if(date.isBefore(LocalDate.now())) {
            return new ArrayList<>();
        }
        List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityRepository.findDoctorAvailabilityByDate(doctor, date);
        List<Appointment> doctorAppointmentList = appointmentRepository.findDoctorAppointmentsByDate(doctor, date, bookedAppointmentStatusTypeList);

        Set<LocalTime> takenTimeSlots = new HashSet<>();
        if(!doctorAppointmentList.isEmpty()) {
            takenTimeSlots = doctorAppointmentList.stream()
                    .map(appointment -> appointment.getAppointmentStartDate().toLocalTime())
                    .collect(Collectors.toSet());
        }
        List<LocalTime> allTimeSlots = new ArrayList<>();
        if(!doctorAvailabilityList.isEmpty()) {
            doctorAvailabilityList.forEach(doctorAvailability -> {
                LocalTime startTime = date.isAfter(LocalDate.now()) ? doctorAvailability.getStartDateTime().toLocalTime() : LocalTime.now();
                int startHour = startTime.getMinute() == 0 ? startTime.getHour() : startTime.getHour()+1;
                startTime = LocalTime.of(startHour, 0);
                List<LocalTime> timeSlots = createTimeSlotsForDay(startTime, doctorAvailability.getEndDateTime().toLocalTime(), 60);
                allTimeSlots.addAll(timeSlots);
            });
        } else {
            return new ArrayList<>();
        }

        allTimeSlots.removeAll(takenTimeSlots);

        AtomicLong count = new AtomicLong(0);
        return allTimeSlots.stream()
                .map(time -> {
                    count.getAndIncrement();
                    return AppointmentSlotDto.builder()
                            .id(count.get())
                            .date(date)
                            .time(time)
                            .doctor(map(doctor, UserDto.class))
                            .available(Boolean.TRUE)
                            .build();
                })
                .toList();
    }

    @Override
    public ReserveAppointmentResponseDto reserveDoctorAppointment(ReserveDoctorAppointmentRequestDto request) {
        User doctor = userRepository.findById(Long.valueOf(request.getDoctorId())).get();
        User patint = userRepository.findById(Long.valueOf(request.getUserId())).get();
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patint)
                .reservationCode(ReservationCodeGenerator.generateReservationCode())
                .appointmentStartDate(LocalDateTime.of(request.getDate(), request.getTime()))
                .appointmentStatus(AppointmentStatusType.CONFIRMED)
                .build();
        appointment = appointmentRepository.save(appointment);
        emailService.sendBookedReservationEmail(map(appointment, AppointmentDto.class));
        return ReserveAppointmentResponseDto.builder()
                .reservationCode(appointment.getReservationCode())
                .build();
    }

    @Override
    public List<AppointmentSlotDto> getDeviceAvailability(Long deviceId, String dateString) {
        DeviceEntity device = deviceRepository.findById(deviceId).get();
        LocalDate date = LocalDate.parse(dateString, DATE_TIME_FORMATTER);
        if(date.isBefore(LocalDate.now())) {
            return new ArrayList<>();
        }
        List<Appointment> deviceAppointmentList = appointmentRepository.findDeviceAppointmentsByDate(device, date, bookedAppointmentStatusTypeList);

        LocalTime startTime = date.isAfter(LocalDate.now()) ? LocalTime.MIDNIGHT : LocalTime.now();
        int startHour = startTime.getMinute() == 0 ? startTime.getHour() : startTime.getHour()+1;
        startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(23, 59);
        List<LocalTime> allTimeSlots = createTimeSlotsForDay(startTime, endTime, 60);

        Set<LocalTime> takenTimeSlots = deviceAppointmentList.stream()
                .map(appointment -> appointment.getAppointmentStartDate().toLocalTime())
                .collect(Collectors.toSet());

        allTimeSlots.removeAll(takenTimeSlots);

        AtomicLong count = new AtomicLong(0);
        return allTimeSlots.stream()
                .map(time -> {
                    count.getAndIncrement();
                    return AppointmentSlotDto.builder()
                        .id(count.get())
                        .date(date)
                        .time(time)
                        .doctor(null)
                        .available(Boolean.TRUE)
                        .build();
                })
                .toList();
    }

    private List<LocalTime> createTimeSlotsForDay(LocalTime startTime, LocalTime endTime, long parseMinute) {
        List<LocalTime> timeSlots = new ArrayList<>();

        LocalTime limitTime = endTime.minusMinutes(parseMinute);
        while (!startTime.isAfter(endTime)) {
            timeSlots.add(startTime);
            if(startTime.isAfter(limitTime)) {
                break;
            }
            startTime = startTime.plusMinutes(parseMinute);
        }

        return timeSlots;
    }

    @Override
    public ReserveAppointmentResponseDto reserveDeviceAppointment(ReserveDeviceAppointmentRequestDto request) {
        DeviceEntity device = deviceRepository.findById(Long.valueOf(request.getDeviceId())).get();
        User patint = userRepository.findById(Long.valueOf(request.getUserId())).get();
        Appointment appointment = Appointment.builder()
                .device(device)
                .patient(patint)
                .reservationCode(ReservationCodeGenerator.generateReservationCode())
                .appointmentStartDate(LocalDateTime.of(request.getDate(), request.getTime()))
                .appointmentStatus(AppointmentStatusType.CONFIRMED)
                .build();
        appointment = appointmentRepository.save(appointment);
        emailService.sendBookedReservationEmail(map(appointment, AppointmentDto.class));
        return ReserveAppointmentResponseDto.builder()
                .reservationCode(appointment.getReservationCode())
                .build();
    }

    @Override
    public List<UserAppointmentResponseDto> getUserAppointmentList(Long id) {
        User patient = userRepository.findById(id).get();
        List<Appointment> appointmentList = appointmentRepository.findPatientActiveAppointments(patient, LocalDateTime.now(), bookedAppointmentStatusTypeList);
        return appointmentList.stream()
                .map(appointment -> {
                    String type = Objects.nonNull(appointment.getDoctor()) ? "doctor" : "device";
                    String name = Objects.nonNull(appointment.getDoctor()) ? "Dr. " +appointment.getDoctor().getUsername() + " / " + appointment.getDoctor().getDoctorDetail().getSpecialty().toString() : appointment.getDevice().getName();
                    return UserAppointmentResponseDto.builder()
                            .id(appointment.getId())
                            .type(type)
                            .name(name)
                            .date(appointment.getAppointmentStartDate().toLocalDate())
                            .time(appointment.getAppointmentStartDate().toLocalTime())
                            .reservationCode(appointment.getReservationCode())
                            .build();
                })
                .toList();
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setAppointmentStatus(AppointmentStatusType.CANCELLED);
        emailService.cancelReservationEmail(map(appointment, AppointmentDto.class));
        appointmentRepository.save(appointment);
    }

    @Override
    public void createDoctorCalendar(CreateDoctorCalendarRequestDto request) {
        User doctor = userRepository.findById(request.getDoctorId()).get();
        DoctorAvailability doctorAvailability = DoctorAvailability.builder()
                .doctor(doctor)
                .startDateTime(LocalDateTime.of(request.getDate(), request.getStartTime()))
                .endDateTime(LocalDateTime.of(request.getDate(), request.getEndTime()))
                .isActive(Boolean.TRUE)
                .build();
        doctorAvailabilityRepository.save(doctorAvailability);
    }

    @Override
    public List<DoctorCalendarResponseDto> findDoctorCalendarByDate(Long doctorId, LocalDate date) {
        User doctor = userRepository.findById(doctorId).get();
        List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityRepository.findDoctorAvailabilityByDate(doctor, date);
        return doctorAvailabilityList.stream()
                .map(doctorAvailability -> DoctorCalendarResponseDto.builder()
                        .id(doctorAvailability.getId())
                        .date(doctorAvailability.getStartDateTime().toLocalDate())
                        .startTime(doctorAvailability.getStartDateTime().toLocalTime())
                        .endTime(doctorAvailability.getEndDateTime().toLocalTime())
                        .build())
                .toList();
    }

    @Override
    public Boolean deleteDoctorCalendar(Long id) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findById(id).get();
        User doctor = doctorAvailability.getDoctor();
        boolean existsAnyActiveAppointment = appointmentRepository.existsDoctorAppointmentsInDateRange(doctor, doctorAvailability.getStartDateTime(),
                doctorAvailability.getEndDateTime(), bookedAppointmentStatusTypeList);
        if(existsAnyActiveAppointment) {
            return Boolean.FALSE;
        } else {
            doctorAvailability.setIsActive(Boolean.FALSE);
            doctorAvailabilityRepository.save(doctorAvailability);
            return Boolean.TRUE;
        }
    }

    @Override
    public List<BookedDoctorAppointmentResponseDto> getBookedDoctorAppointments(Long id) {
        User doctor = userRepository.findById(id).get();
        List<Appointment> bookedDoctorAppointmentList = appointmentRepository.findActiveBookedDoctorAppointments(doctor, bookedAppointmentStatusTypeList);
        return bookedDoctorAppointmentList.stream()
                .map(bookedDoctorAppointment -> BookedDoctorAppointmentResponseDto.builder()
                        .id(bookedDoctorAppointment.getId())
                        .date(bookedDoctorAppointment.getAppointmentStartDate().toLocalDate())
                        .startTime(bookedDoctorAppointment.getAppointmentStartDate().toLocalTime())
                        .patientName(bookedDoctorAppointment.getPatient().getUsername())
                        .build())
                .toList();
    }
}
