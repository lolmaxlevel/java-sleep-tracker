package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> ANALYSES =
            List.of(
                    new SessionCountAnalysis(),
                    new MinimumDurationAnalysis(),
                    new MaximumDurationAnalysis(),
                    new AverageDurationAnalysis(),
                    new BadQualityCountAnalysis(),
                    new SleeplessNightsAnalysis(),
                    new ChronotypeAnalysis()
            );

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Использование: SleepTrackerApp <путь к файлу лога сна>");
            return;
        }
        try {
            List<SleepingSession> sessions = new SleepLogReader().read(Path.of(args[0]));
            ANALYSES.stream()
                    .map(analysis -> analysis.apply(sessions))
                    .forEach(result -> System.out.println(result.description() + ": " + result.value()));
        } catch (IOException | IllegalArgumentException exception) {
            System.err.println("Не удалось проанализировать лог сна: " + exception.getMessage());
        }
    }
}
