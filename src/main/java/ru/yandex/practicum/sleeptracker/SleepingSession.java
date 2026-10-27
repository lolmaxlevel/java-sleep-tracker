package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public record SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality quality) {
    public SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality quality) {
        this.start = Objects.requireNonNull(start, "Время засыпания обязательно");
        this.end = Objects.requireNonNull(end, "Время пробуждения обязательно");
        this.quality = Objects.requireNonNull(quality, "Качество сна обязательно");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Пробуждение должно быть позже засыпания");
        }
    }

    public long getDurationMinutes() {
        return Duration.between(start, end).toMinutes();
    }
}
