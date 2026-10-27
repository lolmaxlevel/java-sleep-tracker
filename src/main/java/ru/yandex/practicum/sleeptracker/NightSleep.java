package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

class NightSleep {
    static List<LocalDate> getNights(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return List.of();
        }

        LocalDateTime start = sessions.getFirst().start();
        LocalDateTime end = sessions.getLast().end();
        LocalDate firstNight = start.toLocalDate();
        if (!start.toLocalTime().isBefore(LocalTime.NOON)) {
            firstNight = firstNight.plusDays(1);
        }

        LocalDate endDate = end.toLocalDate();
        if (!end.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            endDate = endDate.plusDays(1);
        }

        if (!firstNight.isBefore(endDate)) {
            return List.of();
        }
        return firstNight.datesUntil(endDate).collect(Collectors.toList());
    }

    static List<SleepingSession> getSessions(LocalDate date, List<SleepingSession> sessions) {
        LocalDateTime midnight = date.atStartOfDay();
        LocalDateTime six = date.atTime(6, 0);
        return sessions.stream()
                .filter(session -> session.start().isBefore(six) && session.end().isAfter(midnight))
                .collect(Collectors.toList());
    }
}
