package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;
import java.util.List;

public class FuncSessionCount implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult<>("количество сессий", sleepingSessions.size());
    }
}