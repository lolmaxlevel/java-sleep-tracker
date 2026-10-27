package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.stream.Collectors;

public class SleepLogReader {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter
            .ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> read(Path path) throws IOException {
        return Files.readAllLines(path).stream()
                .filter(line -> !line.isBlank())
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private SleepingSession parseLine(String line) {
        try {
            String[] parts = line.split(";", -1);
            if (parts.length != 3) {
                throw new IllegalArgumentException("В записи должно быть три поля");
            }
            LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMAT);
            LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMAT);
            SleepQuality quality = SleepQuality.valueOf(parts[2].trim());
            return new SleepingSession(start, end, quality);
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException("Неверная запись: " + line, exception);
        }
    }
}
