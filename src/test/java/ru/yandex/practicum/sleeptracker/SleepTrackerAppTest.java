package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    List<SleepingSession> testListSession = List.of(
            new SleepingSession(
                LocalDateTime.of(2026, 9, 12, 12, 0),
                LocalDateTime.of(2026, 9, 12, 13, 0),
                "GOOD"
            ),
            new SleepingSession(
                LocalDateTime.of(2026, 9, 12, 12, 0),
                LocalDateTime.of(2026, 9, 12, 12, 20),
                "BAD"
            ),
            new SleepingSession(
                LocalDateTime.of(2026, 9, 12, 12, 0),
                LocalDateTime.of(2026, 9, 12, 12, 10),
                "BAD"
            )
    );

    @Test
    void shouldBeZeroForAverageValue() {
        FuncAverageSession average = new FuncAverageSession();
        SleepAnalysisResult<Double> result = average.apply(List.of());
        assertEquals(0.0, result.getValue());
    }

    @Test
    void shouldBeAverageResultValue() {
        FuncAverageSession average = new FuncAverageSession();
        SleepAnalysisResult<Double> result = average.apply(testListSession);
        assertEquals(30.0, result.getValue());
    }

    @Test
    void shouldBeZeroForMaxMinValueAndBadSessionCount() {
        FuncMaxSession max = new FuncMaxSession();
        SleepAnalysisResult<Double> resultMax = max.apply(List.of());
        assertEquals(0.0, resultMax.getValue());

        FuncMinSession min = new FuncMinSession();
        SleepAnalysisResult<Double> resultMin = min.apply(List.of());
        assertEquals(0.0, resultMin.getValue());

        FuncBadSessionCount badSession = new FuncBadSessionCount();
        SleepAnalysisResult<Integer> resultBad = badSession.apply(List.of());
        assertEquals(0, resultBad.getValue());
    }

    @Test
    void shouldBeMax60Min10ResultValue() {
        FuncMaxSession max = new FuncMaxSession();
        FuncMinSession min = new FuncMinSession();

        SleepAnalysisResult<Double> resultMax = max.apply(testListSession);
        assertEquals(60.0, resultMax.getValue());

        SleepAnalysisResult<Double> resultMin = min.apply(testListSession);
        assertEquals(10.0, resultMin.getValue());
    }

    @Test
    void shouldBeBadCountEqualTwo() {
        FuncBadSessionCount bad = new FuncBadSessionCount();

        SleepAnalysisResult<Integer> resultBad = bad.apply(testListSession);
        assertEquals(2, resultBad.getValue());
    }

    @Test
    void shouldBeCorrectCountForSleepNights() {
        List<SleepingSession> testListSleepNights = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 11, 21, 0),
                        LocalDateTime.of(2026, 9, 12, 3, 10),
                        "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 12, 22, 0),
                        LocalDateTime.of(2026, 9, 13, 13, 0),
                        "GOOD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 14, 5, 0),
                        LocalDateTime.of(2026, 9, 14, 12, 20),
                        "BAD"
                )
        );
        FuncSleeplessNights sleeplessNights = new FuncSleeplessNights();
        SleepAnalysisResult<Integer> result = sleeplessNights.apply(testListSleepNights);
        assertEquals(0, result.getValue());
    }

    @Test
    void shouldBeCorrectCountForSleeplessNights() {
        List<SleepingSession> testListSleeplessNights = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 11, 6, 1),
                        LocalDateTime.of(2026, 9, 11, 11, 59),
                        "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 12, 12, 0),
                        LocalDateTime.of(2026, 9, 12, 13, 0),
                        "GOOD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 13, 21, 1),
                        LocalDateTime.of(2026, 9, 13, 23, 59),
                        "BAD"
                )
        );
        FuncSleeplessNights sleeplessNights = new FuncSleeplessNights();
        SleepAnalysisResult<Integer> result = sleeplessNights.apply(testListSleeplessNights);
        assertEquals(3, result.getValue());
    }

    @Test
    void shouldBeZeroSleepLessNights() {
        FuncSleeplessNights sleeplessNights = new FuncSleeplessNights();
        SleepAnalysisResult<Integer> result = sleeplessNights.apply(List.of());
        assertEquals(0, result.getValue());
    }

    @Test
    void shouldBeChronotypeOWL() {
        List<SleepingSession> testListChronotype = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 11, 23, 1),
                        LocalDateTime.of(2026, 9, 12, 11, 0),
                        "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 13, 1, 0),
                        LocalDateTime.of(2026, 9, 13, 9, 1),
                        "GOOD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 14, 3, 0),
                        LocalDateTime.of(2026, 9, 14, 12, 59),
                        "BAD"
                )
        );
        FuncClassifierChronotype chronotype =  new FuncClassifierChronotype();
        SleepAnalysisResult<Chronotype> result = chronotype.apply(testListChronotype);
        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void shouldBeChronotypeLARK() {
        List<SleepingSession> testListChronotype = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 11, 21, 1),
                        LocalDateTime.of(2026, 9, 12, 6, 59),
                        "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 13, 19, 0),
                        LocalDateTime.of(2026, 9, 13, 5, 1),
                        "GOOD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 14, 21, 59),
                        LocalDateTime.of(2026, 9, 14, 4, 59),
                        "BAD"
                )
        );
        FuncClassifierChronotype chronotype =  new FuncClassifierChronotype();
        SleepAnalysisResult<Chronotype> result = chronotype.apply(testListChronotype);
        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    void shouldBeChronotypePIGEON() {
        List<SleepingSession> testListChronotype = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 11, 23, 1),
                        LocalDateTime.of(2026, 9, 12, 9, 1),
                        "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 13, 1, 0),
                        LocalDateTime.of(2026, 9, 13, 12, 1),
                        "GOOD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 14, 21, 59),
                        LocalDateTime.of(2026, 9, 14, 4, 59),
                        "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 9, 14, 21, 59),
                        LocalDateTime.of(2026, 9, 14, 4, 59),
                        "BAD"
                )
        );
        FuncClassifierChronotype chronotype =  new FuncClassifierChronotype();
        SleepAnalysisResult<Chronotype> result = chronotype.apply(testListChronotype);
        assertEquals(Chronotype.PIGEON, result.getValue());
    }
}