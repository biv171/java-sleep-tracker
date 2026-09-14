package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;
import java.util.List;

public class FuncMaxSession implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {
    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sleepingSessions) {
        double maxSession = sleepingSessions.stream()
                .mapToDouble(SleepingSession::getDuration)
                .max().orElse(0.0);
        return new SleepAnalysisResult<>("max продолжительность сессии", maxSession);
    }
}