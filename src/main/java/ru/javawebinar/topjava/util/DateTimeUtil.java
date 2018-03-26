package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenDate(LocalDate lt, LocalDate startDate, LocalDate endDate) {
        return lt.compareTo(startDate) >= 0 && lt.compareTo(endDate) <= 0;
    }

    public static boolean isBetweenDateTime(TemporalAccessor lt, TemporalAccessor start, TemporalAccessor end) {
           //lt instanceof LocalDate
        LocalDate l = LocalDate.from(lt);
        LocalDate st = LocalDate.from(start);
        LocalDate en = LocalDate.from(end);

        return l.compareTo(st) >= 0 && l.compareTo(en) <= 0;

    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static void main(String[] args) {
        isBetweenDateTime(
                LocalDate.of(2005, Month.JANUARY,1),
                LocalDate.of(2000, Month.JANUARY,1),
                LocalDate.of(2020, Month.JANUARY,1)
                );


        isBetweenDateTime(
                LocalTime.of(2, 15,20),
                LocalTime.of(1, 40,15),
                LocalTime.of(3, 1,1)
        );
    }


}
