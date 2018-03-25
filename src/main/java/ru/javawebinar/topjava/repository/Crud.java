package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface Crud {

    Meal deleteById(int id);
    void update(Meal meal);
    List<Meal> getAll();
    Meal getById(int id);
}
