package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeAnalysis implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final LocalTime ELEVEN_PM = LocalTime.of(23, 0);
    private static final LocalTime NINE_AM = LocalTime.of(9, 0);
    private static final LocalTime TEN_PM = LocalTime.of(22, 0);
    private static final LocalTime SEVEN_AM = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<Chronotype> types = NightSleep.getNights(sessions).stream()
                .map(date -> NightSleep.getSessions(date, sessions))
                .filter(night -> !night.isEmpty())
                .map(this::getChronotype)
                .toList();

        long owls = types.stream().filter(type -> type == Chronotype.OWL).count();
        long larks = types.stream().filter(type -> type == Chronotype.LARK).count();
        long doves = types.stream().filter(type -> type == Chronotype.DOVE).count();

        Chronotype result = Chronotype.DOVE;
        if (owls > larks && owls > doves) {
            result = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            result = Chronotype.LARK;
        }
        return new SleepAnalysisResult("Хронотип пользователя", result);
    }

    private Chronotype getChronotype(List<SleepingSession> night) {
        LocalTime start = night.getFirst().start().toLocalTime();
        LocalTime end = night.getLast().end().toLocalTime();

        boolean lateStart = start.isAfter(ELEVEN_PM) || start.isBefore(LocalTime.NOON);
        if (lateStart && end.isAfter(NINE_AM)) {
            return Chronotype.OWL;
        }
        if (!start.isBefore(LocalTime.NOON) && start.isBefore(TEN_PM)
                && end.isBefore(SEVEN_AM)) {
            return Chronotype.LARK;
        }
        return Chronotype.DOVE;
    }
}
