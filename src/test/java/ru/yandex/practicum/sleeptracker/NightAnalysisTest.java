package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NightAnalysisTest {
    private SleepingSession session(String start, String end) {
        return new SleepingSession(LocalDateTime.parse(start), LocalDateTime.parse(end), SleepQuality.GOOD);
    }

    @Test
    void emptyLogHasNoNights() {
        assertEquals(0L, new SleeplessNightsAnalysis().apply(List.of()).value());
    }

    @Test
    void nightSleepIsNotSleepless() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:00", "2025-10-02T03:00"),
                session("2025-10-03T02:00", "2025-10-03T07:00"));
        assertEquals(0L, new SleeplessNightsAnalysis().apply(sessions).value());
    }

    @Test
    void sleepAtSixDoesNotCountAsNightSleep() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-02T06:00", "2025-10-02T11:00"));
        assertEquals(1L, new SleeplessNightsAnalysis().apply(sessions).value());
    }

    @Test
    void countsMissingNightsAcrossMonths() {
        List<SleepingSession> sessions = List.of(
                session("2025-01-30T23:00", "2025-01-31T07:00"),
                session("2025-02-02T23:00", "2025-02-03T07:00"));
        assertEquals(2L, new SleeplessNightsAnalysis().apply(sessions).value());
    }

    @Test
    void detectsOwlAndIgnoresDaytimeSleep() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T23:30", "2025-10-02T10:00"),
                session("2025-10-02T14:00", "2025-10-02T16:00"));
        assertEquals(Chronotype.OWL, new ChronotypeAnalysis().apply(sessions).value());
    }

    @Test
    void detectsLark() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T21:30", "2025-10-02T06:00"));
        assertEquals(Chronotype.LARK, new ChronotypeAnalysis().apply(sessions).value());
    }

    @Test
    void detectsDove() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T22:30", "2025-10-02T08:00"));
        assertEquals(Chronotype.DOVE, new ChronotypeAnalysis().apply(sessions).value());
    }

    @Test
    void tieDefaultsToDove() {
        List<SleepingSession> sessions = List.of(
                session("2025-10-01T21:30", "2025-10-02T06:00"),
                session("2025-10-02T23:30", "2025-10-03T10:00"));
        assertEquals(Chronotype.DOVE, new ChronotypeAnalysis().apply(sessions).value());
    }
}
