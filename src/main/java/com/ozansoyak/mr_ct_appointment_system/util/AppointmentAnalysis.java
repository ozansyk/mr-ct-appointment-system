package com.ozansoyak.mr_ct_appointment_system.util;

import com.ozansoyak.mr_ct_appointment_system.model.Appointment;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentAnalysis {

    public static AppointmentSummary analyzeAppointments(List<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            throw new IllegalArgumentException("Randevu listesi boş olamaz.");
        }

        Map<DayOfWeek, Long> dayOfWeekCounts = appointments.stream()
                .collect(Collectors.groupingBy(
                        appointment -> appointment.getAppointmentStartDate().getDayOfWeek(),
                        Collectors.counting()
                ));

        List<DayFrequency> sortedDays = dayOfWeekCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> new DayFrequency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        Map<LocalTime, Long> timeFrequency = appointments.stream()
                .map(appointment -> appointment.getAppointmentStartDate().toLocalTime())
                .collect(Collectors.groupingBy(time -> time, Collectors.counting()));

        LocalTime mostFrequentTime = timeFrequency.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(LocalTime.MIN);

        boolean allUniqueTimes = timeFrequency.values().stream().allMatch(count -> count == 1);

        LocalTime referenceTime;
        if (allUniqueTimes) {
            referenceTime = appointments.stream()
                    .map(appointment -> appointment.getAppointmentStartDate().toLocalTime())
                    .reduce(LocalTime.MIN, (sum, time) -> sum.plusSeconds(time.toSecondOfDay() / appointments.size()));
        } else {
            referenceTime = mostFrequentTime;
        }

        return new AppointmentSummary(sortedDays, referenceTime, mostFrequentTime);
    }

    @Getter
    public static class DayFrequency {
        private final DayOfWeek day;
        private final long frequency;

        public DayFrequency(DayOfWeek day, long frequency) {
            this.day = day;
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return day + ": " + frequency;
        }
    }

    @Getter
    public static class AppointmentSummary {
        private final List<DayFrequency> sortedDays;
        private final LocalTime referenceTime;
        private final LocalTime mostFrequentTime;

        public AppointmentSummary(List<DayFrequency> sortedDays, LocalTime referenceTime, LocalTime mostFrequentTime) {
            this.sortedDays = sortedDays;
            this.referenceTime = referenceTime;
            this.mostFrequentTime = mostFrequentTime;
        }

        @Override
        public String toString() {
            return "Gün sıralaması: " + sortedDays +
                    ", Referans saat: " + referenceTime +
                    ", En çok randevu alınan saat: " + mostFrequentTime;
        }
    }
}
