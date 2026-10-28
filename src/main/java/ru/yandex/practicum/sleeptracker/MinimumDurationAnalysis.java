package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinimumDurationAnalysis
        implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult("Минимальная продолжительность сессии (мин)",
                sessions.stream().mapToLong(SleepingSession::getDurationMinutes).min().orElse(0));
    }
}
