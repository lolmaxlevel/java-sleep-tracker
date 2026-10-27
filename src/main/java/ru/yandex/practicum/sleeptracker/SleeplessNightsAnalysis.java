package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleeplessNightsAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = NightSleep.getNights(sessions).stream()
                .filter(date -> NightSleep.getSessions(date, sessions).isEmpty())
                .count();
        return new SleepAnalysisResult("Количество бессонных ночей", count);
    }
}
