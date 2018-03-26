package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, Integer userId);

    Meal delete(int id, Integer userId);

    Meal get(int id, Integer userId);

    Collection<Meal> getAll(Integer userId);

    Collection<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer userId);
}
