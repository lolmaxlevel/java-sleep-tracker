package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationAnalysis
        implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult("Средняя продолжительность сессии (мин)",
                sessions.stream().mapToLong(SleepingSession::getDurationMinutes).average().orElse(0.0));
    }
}
