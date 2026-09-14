package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class FuncBadSessionCount implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        int badSessionCount = (int) sleepingSessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult<>("количество сессий плохого сна", badSessionCount);
    }
}