package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;


public interface MealService {

    Meal create(Meal meal, Integer userId) throws NotFoundException;

    Meal update(Meal meal, Integer userId) throws NotFoundException;

    Meal delete(int id, Integer userId) throws NotFoundException;

    Meal get(int id, Integer userId) throws NotFoundException;

    Collection<Meal> getAll(Integer userId);

}