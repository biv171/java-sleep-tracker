package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;


public class SleepTrackerApp {
    static final String fileName = "sleep_log.txt";
    static List<SleepingSession> sleepingSession = new ArrayList<>();
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            List<String> source = reader.lines().toList();
            sleepingSession = source.stream()
                    .map(line -> line.split(";"))
                    .map(line -> {
                        LocalDateTime startDateTime = LocalDateTime.parse(line[0], dateFormatter);
                        LocalDateTime endDateTime = LocalDateTime.parse(line[1], dateFormatter);
                        return new SleepingSession(startDateTime,endDateTime,line[2]);
                    })
                    .collect(Collectors.toList());

            //список всех функций трекера
            List<Function> funcList = new ArrayList<>();
            //определяем хронотип
            funcList.add(new FuncClassifierChronotype());
            //всего бессонных ночей
            funcList.add(new FuncSleeplessNights());
            //всего было сессий сна
            funcList.add(new FuncSessionCount());
            //средняя продолжительность сессии (в минутах)
            funcList.add(new FuncAverageSession());
            //количество сессий с плохим качеством сна.
            funcList.add(new FuncBadSessionCount());
            //максимальная продолжительность сессии (в минутах)
            funcList.add(new FuncMaxSession());
            //минимальная продолжительность сессии (в минутах);
            funcList.add(new FuncMinSession());

            funcList.stream()
                .map(session -> session.apply(sleepingSession))
                .forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("IOException ошибка чтения файла");
        }
    }
}