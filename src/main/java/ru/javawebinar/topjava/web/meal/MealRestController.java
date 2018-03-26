package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }


  public Meal create(Meal meal, Integer userId) {
      log.info("create {}", meal);
      ValidationUtil.checkNew(meal);
        return service.create(meal, userId);
    }


    public Meal update(Meal meal, Integer userId) throws NotFoundException {
        log.info("update {} with id={}", meal, userId);
       // ValidationUtil.assureIdConsistent(meal, userId);//TODO с комментом раюотает норм
        return service.update(meal,userId);
    }


    public Meal delete(int id, Integer userId) throws NotFoundException {
      log.info("delete {}", id);
        return service.delete(id,userId);
    }


    public Meal get(int id, Integer userId) throws NotFoundException {
        log.info("get {}", id);
        return service.get(id, userId);
    }


    public Collection<MealWithExceed> getAll(Integer userId) {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public Collection<MealWithExceed> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer userId){
        log.info("getFiltered");
        return MealsUtil.getWithExceeded(
                service.getFiltered(startDate, endDate, startTime, endTime, userId),
                AuthorizedUser.getCaloriesPerDay());
    }
}