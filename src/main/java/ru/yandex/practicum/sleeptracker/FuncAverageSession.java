package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;
import java.util.List;

//средняя продолжительность сессии (в минутах);
public class FuncAverageSession implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {
    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sleepingSessions) {
        double average = sleepingSessions.stream()
                .mapToDouble(SleepingSession::getDuration)
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult<>("среднее количество минут сна", average);
    }
}