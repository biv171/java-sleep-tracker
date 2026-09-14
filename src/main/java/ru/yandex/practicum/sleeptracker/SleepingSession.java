package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.Duration;

public class SleepingSession {
    LocalDateTime startDT;
    LocalDateTime endDT;
    SleepQuality quality;

    public SleepingSession(LocalDateTime startDT, LocalDateTime endDT, SleepQuality quality) {
        this.startDT = startDT;
        this.endDT = endDT;
        this.quality = quality;
    }

    public double getDuration() {
        return Duration.between(startDT, endDT).toMinutes();
    }

    public LocalDateTime getStartDT() {
        return startDT;
    }

    public LocalDateTime getEndDT() {
        return endDT;
    }

    public SleepQuality getQuality() {
        return quality;
    }
}