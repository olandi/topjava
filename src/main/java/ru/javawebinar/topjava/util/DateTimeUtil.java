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

    public static<T extends Comparable<T>> boolean isBetweenDateTime(T lt, T start, T end) {

        return lt.compareTo(start) >= 0 && lt.compareTo(end) <= 0;
    }

    //Todo обработка ошибок
    public static LocalDate stringToLocalDate(String date){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        //String date = "16/08/2016";

        //convert String to LocalDate
        return LocalDate.parse(date/*, formatter*/);
    }
    public static LocalTime stringToLocalTime(String time){return LocalTime.parse(time/*, formatter*/);}

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
        System.out.println(stringToLocalDate("1234-02-05"));
        System.out.println(stringToLocalTime("10:02"));

    }


}
