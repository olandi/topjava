package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CrudImpl implements Crud {
    private CopyOnWriteArrayList<Meal> repository = new CopyOnWriteArrayList<>(MealsUtil.getMealsData());

    private AtomicInteger idCounter = new AtomicInteger(repository.stream().max(Comparator.comparingInt(Meal::getId)).get().getId());

    @Override
    public Meal deleteById(int id) {
        Meal meal = getById(id);
        repository.remove(meal);
        return meal;
    }

    @Override
    public void update(Meal meal) {
        if (meal.getId() != null) {
            repository.set(repository.indexOf(getById(meal.getId())), meal);
        } else {
            meal.setId(idCounter.getAndIncrement());
            repository.add(meal);
        }
    }

    @Override
    public List<Meal> getAll() {
       // return MealsUtil.getFilteredWithExceeded(repository, LocalTime.MIN, LocalTime.MAX, 2000);
        return repository;
    }

    public Meal getById(int id) {
        return repository.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

}
