package ru.yandex.practicum.sleeptracker;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.time.*;

public class FuncSleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    LocalDate startDate;
    LocalDate endDate;

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult<>(
                    "Бессонных ночей", 0);
        }

        //считаем общее кол-во ночей
        LocalTime startTime = sleepingSessions.getFirst().getStartDT().toLocalTime();
        if (startTime.isAfter(LocalTime.NOON)) {
            startDate = sleepingSessions.getFirst().getStartDT().toLocalDate();
        } else {
            startDate = sleepingSessions.getFirst().getStartDT().toLocalDate().minusDays(1);
        }
        endDate = sleepingSessions.getLast().getEndDT().toLocalDate();

        int totalNights = (int)ChronoUnit.DAYS.between(startDate, endDate);

        //считаем кол-во нормальных ночей
        int sleepNights = (int)sleepingSessions.stream()
                .filter(session -> (
                            session.getEndDT().toLocalTime().isBefore(LocalTime.of(6,0)) ||
                            session.getStartDT().toLocalTime().isBefore(LocalTime.of(6,0)) ||
                            session.getStartDT().toLocalDate().isBefore(session.getEndDT().toLocalDate())
                ))
                .count();
        return new SleepAnalysisResult<>(
                "Общее количество бессоных ночей", totalNights - sleepNights);
    }
}