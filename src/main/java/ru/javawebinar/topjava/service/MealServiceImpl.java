package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

public class MealServiceImpl implements MealService {

    private MealRepository repository;


    @Override
    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    @Override
    public Meal update(Meal meal, Integer userId) throws NotFoundException {
        return checkNotFound(repository.save(meal, userId), "with mealId=" + meal.getId() + " for user id=" + userId);
    }

    @Override
    public Meal delete(int id, Integer userId) throws NotFoundException {
        return checkNotFound(repository.delete(id, userId), "with mealId=" + id + " for user id=" + userId);
    }

    @Override
    public Meal get(int id, Integer userId) throws NotFoundException {
        return checkNotFound(repository.get(id, userId), "with mealId=" + id + " for user id=" + userId);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }
}