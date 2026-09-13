package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.function.Function;
import java.util.List;

public class FuncClassifierChronotype implements Function<List<SleepingSession>, SleepAnalysisResult<Chronotype>> {
    public SleepAnalysisResult<Chronotype> apply(List<SleepingSession> sleepingSessions) {
        Chronotype resultChronotype;
        //Все нормальные ночи
        List<SleepingSession> sleepNights = sleepingSessions.stream()
            .filter(session -> (
                    session.getEndDT().toLocalTime().isBefore(LocalTime.of(6,0)) ||
                    session.getStartDT().toLocalTime().isBefore(LocalTime.of(6,0)) ||
                    session.getStartDT().toLocalDate().isBefore(session.getEndDT().toLocalDate())
                )
            )
            .toList();
        int nightsCount = sleepNights.size();

        //owl: startDT > 23:00, endtDT > 9:00
        int owlCount = (int)sleepNights.stream()
            .filter(session -> (
                    session.startDT.toLocalTime().isAfter(LocalTime.of(23,0)) ||
                    session.startDT.toLocalTime().isAfter(LocalTime.of(0,0)) &&
                    session.endDT.toLocalTime().isAfter(LocalTime.of(9,0))
            ))
            .count();

        //lark: startDT < 22:00, endDT < 7:00
        int larkCount = (int)sleepNights.stream()
            .filter(session -> (
                session.startDT.toLocalTime().isBefore(LocalTime.of(22,0)) &&
                session.endDT.toLocalTime().isBefore(LocalTime.of(7,0))
            ))
            .count();

        //остальные pigeon
        int pigeonCount = nightsCount - owlCount - larkCount;

        if (owlCount > larkCount && owlCount > pigeonCount) {
             resultChronotype = Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > pigeonCount) {
            resultChronotype = Chronotype.LARK;
        } else {
            resultChronotype = Chronotype.PIGEON;
        }

        return new SleepAnalysisResult<>("Твой хронотип:", resultChronotype);
    }
}