package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal DB_MEAL_1 = new Meal(100002, LocalDateTime.parse("2011-05-16T15:36"),"Завтрак",234);
    public static final Meal DB_MEAL_2 = new Meal(100003, LocalDateTime.parse("2012-05-16T15:36"),"Обед",34545);
    public static final Meal DB_MEAL_3 = new Meal(100004, LocalDateTime.parse("2013-05-16T15:36"),"Ужин",345);
    public static final Meal DB_MEAL_4 = new Meal(100005, LocalDateTime.parse("2014-05-16T15:36"),"Завтрак",567);
    public static final Meal DB_MEAL_5 = new Meal(100006, LocalDateTime.parse("2015-05-16T15:36"),"Обед",6786);
    public static final Meal DB_MEAL_6_admin = new Meal(100007, LocalDateTime.parse("2016-05-16T15:36"),"Ужин",3464);



    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }



}
