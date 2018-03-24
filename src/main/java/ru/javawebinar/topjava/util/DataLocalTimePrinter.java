package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.Month;

public class DataLocalTimePrinter {
    public static String printDate(LocalDateTime localDateTime){
        return localDateTime.toLocalDate()+" "+localDateTime.toLocalTime();

    }

    public static void main(String[] args) {
        System.out.println(printDate(LocalDateTime.of(2015, Month.MAY, 30, 10, 0)));
    }
}
