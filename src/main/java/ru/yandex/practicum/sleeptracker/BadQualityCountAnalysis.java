package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualityCountAnalysis
        implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult("Количество сессий с плохим качеством сна",
                sessions.stream().filter(session -> session.quality() == SleepQuality.BAD).count());
    }
}
