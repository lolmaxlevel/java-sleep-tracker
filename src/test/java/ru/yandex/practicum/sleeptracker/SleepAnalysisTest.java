package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SleepAnalysisTest {
    private SleepingSession session(String start, String end, SleepQuality quality) {
        return new SleepingSession(LocalDateTime.parse(start), LocalDateTime.parse(end), quality);
    }

    private final List<SleepingSession> sample = List.of(
            session("2025-10-01T23:00", "2025-10-02T07:00", SleepQuality.GOOD),
            session("2025-10-02T23:00", "2025-10-03T06:00", SleepQuality.BAD),
            session("2025-10-03T14:00", "2025-10-03T14:31", SleepQuality.NORMAL));

    @Test void countsAllSessions() {
        assertEquals(3L, new SessionCountAnalysis().apply(sample).value());
    }
    @Test void countsEmptyLog() {
        assertEquals(0L, new SessionCountAnalysis().apply(List.of()).value());
    }
    @Test void findsMinimumIncludingDaytimeSleep() {
        assertEquals(31L, new MinimumDurationAnalysis().apply(sample).value());
    }
    @Test void minimumForEmptyLogIsZero() {
        assertEquals(0L, new MinimumDurationAnalysis().apply(List.of()).value());
    }
    @Test void findsMaximumAcrossMidnight() {
        assertEquals(480L, new MaximumDurationAnalysis().apply(sample).value());
    }
    @Test void maximumForEmptyLogIsZero() {
        assertEquals(0L, new MaximumDurationAnalysis().apply(List.of()).value());
    }
    @Test void averageKeepsFractionalMinutes() {
        double average = (double) new AverageDurationAnalysis().apply(sample).value();
        assertEquals(931.0 / 3, average, 0.000001);
    }
    @Test void averageForEmptyLogIsZero() {
        assertEquals(0.0, new AverageDurationAnalysis().apply(List.of()).value());
    }
    @Test void countsOnlyBadSessions() {
        assertEquals(1L, new BadQualityCountAnalysis().apply(sample).value());
    }
    @Test void countsNoBadSessionsInEmptyLog() {
        assertEquals(0L, new BadQualityCountAnalysis().apply(List.of()).value());
    }
}
