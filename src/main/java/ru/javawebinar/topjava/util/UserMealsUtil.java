package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),

        new UserMeal(LocalDateTime.of(2015, Month.MAY, 1,7,0), "завтр", 555),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 1,12,0), "обед", 566),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 1,13,0), "ужин", 708),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 1,6,59), "ужин", 444)

        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
List list  =
    mealList.stream().filter( userMeal ->
        ((userMeal.getDateTime().toLocalTime().isAfter(startTime) || userMeal.getDateTime().toLocalTime().equals(startTime)) &&
                (userMeal.getDateTime().toLocalTime().isBefore(endTime) || userMeal.getDateTime().toLocalTime().equals(endTime)))).collect(Collectors.toList());
       // System.out.println(list);


        List list2  =
                mealList.stream().filter( userMeal ->
                        ((userMeal.getDateTime().toLocalTime().isAfter(startTime) || userMeal.getDateTime().toLocalTime().equals(startTime)) &&
                                (userMeal.getDateTime().toLocalTime().isBefore(endTime) || userMeal.getDateTime().toLocalTime().equals(endTime))))
                        .map(u -> new UserMealWithExceed(u.getDateTime(),u.getDescription(),u.getCalories(),true)).collect(Collectors.toList());

        //System.out.println(list2);




        Map<LocalDate,Integer> map =

                mealList.stream().collect(Collectors.toMap(
                        i -> i.getDateTime().toLocalDate(),UserMeal::getCalories, (a, b) -> a + b
                ));
        System.out.println(map);


        List<UserMealWithExceed> list3  =
                mealList.stream().filter( userMeal ->
                        ((userMeal.getDateTime().toLocalTime().isAfter(startTime) || userMeal.getDateTime().toLocalTime().equals(startTime)) &&
                                (userMeal.getDateTime().toLocalTime().isBefore(endTime) || userMeal.getDateTime().toLocalTime().equals(endTime))))
                        .map(u -> new UserMealWithExceed(u.getDateTime(),u.getDescription(),u.getCalories(),
                                map.get(u.getDateTime().toLocalDate())>caloriesPerDay
                        )).collect(Collectors.toList());

        System.out.println(list3);




    return list3;
    }



        public static List<UserMealWithExceed>  getFilteredWithExceededCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMeal> list = new ArrayList<>();
        for (UserMeal m: mealList){
            LocalTime time = m.getDateTime().toLocalTime();
            if ((time.isAfter(startTime)||(time.equals(startTime))) && (time.isBefore(endTime)||(time.equals(endTime)))){
                list.add(m);
            }
        }

        Map<LocalDate,Integer> map = new HashMap<>();
        for (UserMeal m: mealList){
            LocalDate date = m.getDateTime().toLocalDate();

            map.merge(date, m.getCalories(), (a, b) -> a + b);
        }

        List<UserMealWithExceed> resultList = new ArrayList<>();
        for (UserMeal m: list){
            resultList.add(new UserMealWithExceed(m.getDateTime(),m.getDescription(),m.getCalories(),
                    map.get(m.getDateTime().toLocalDate())>caloriesPerDay));
        }

        return resultList;
    }
}
