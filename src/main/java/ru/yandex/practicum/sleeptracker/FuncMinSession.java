package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class FuncMinSession implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {
    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sleepingSessions) {
        double minSession = sleepingSessions.stream()
            .mapToDouble(SleepingSession::getDuration)
            .min().orElse(0.0);
        return new SleepAnalysisResult<>("min продолжительность сессии", minSession);
    }
}