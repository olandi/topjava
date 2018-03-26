package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;


@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

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

    public Collection<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer userId){
        return repository.getFiltered(startDate, endDate, startTime, endTime, userId);
    }
}