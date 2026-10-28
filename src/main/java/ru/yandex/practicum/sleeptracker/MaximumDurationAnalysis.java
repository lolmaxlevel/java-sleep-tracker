package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaximumDurationAnalysis
        implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult("Максимальная продолжительность сессии (мин)",
                sessions.stream().mapToLong(SleepingSession::getDurationMinutes).max().orElse(0));
    }
}
