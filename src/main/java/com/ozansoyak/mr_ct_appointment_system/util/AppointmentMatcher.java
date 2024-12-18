package com.ozansoyak.mr_ct_appointment_system.util;

import com.ozansoyak.mr_ct_appointment_system.dto.reservation.AppointmentSlotDto;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMatcher {

    public static AppointmentSlotDto findBestMatch(
            AppointmentAnalysis.AppointmentSummary appointmentSummary,
            List<AppointmentSlotDto> appointmentSlotDtoList) {

        if (appointmentSlotDtoList == null || appointmentSlotDtoList.isEmpty()) {
            throw new IllegalArgumentException("Randevu slot listesi boş olamaz.");
        }

        if (appointmentSummary == null || appointmentSummary.getSortedDays().isEmpty()) {
            throw new IllegalArgumentException("AppointmentSummary geçerli değil.");
        }

        List<AppointmentSlotDto> filteredByTime = appointmentSlotDtoList.stream()
                .filter(AppointmentSlotDto::isAvailable)
                .filter(slot -> Duration.between(appointmentSummary.getReferenceTime(), slot.getTime()).abs().toHours() <= 3)
                .collect(Collectors.toList());

        if (filteredByTime.isEmpty()) {
            return null;
        }

        return filteredByTime.stream()
                .filter(slot -> slot.getTime().equals(appointmentSummary.getMostFrequentTime()))
                .findFirst()
                .orElseGet(() -> {
                    // Gün sıralamasına göre en uygun slotu al
                    List<AppointmentAnalysis.DayFrequency> sortedDays = appointmentSummary.getSortedDays();
                    return filteredByTime.stream()
                            .min(Comparator.comparing(slot -> sortedDays.stream()
                                    .map(AppointmentAnalysis.DayFrequency::getDay)
                                    .toList()
                                    .indexOf(slot.getDate().getDayOfWeek())))
                            .orElse(null);
                });
    }
}
