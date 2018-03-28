package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }


  public Meal create(Meal meal) {
      log.info("create {}", meal);
      ValidationUtil.checkNew(meal);
        return service.create(meal, AuthorizedUser.id());
    }


    public Meal update(Meal meal, int id) throws NotFoundException {
        log.info("update {} with id={}", meal,id);
        ValidationUtil.assureIdConsistent(meal, id);
        return service.update(meal,AuthorizedUser.id());
    }


    public Meal delete(int id) throws NotFoundException {
      log.info("delete {}", id);
        return service.delete(id,AuthorizedUser.id());
    }


    public Meal get(int id) throws NotFoundException {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }


    public Collection<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public Collection<MealWithExceed> getFiltered(String startDate, String endDate, String startTime, String endTime){
        log.info("getFiltered");

        LocalDate sDate = "".equals(startDate) || startDate == null ? LocalDate.MIN : DateTimeUtil.stringToLocalDate(startDate);
        LocalDate eDate = "".equals(endDate) || endDate == null ? LocalDate.MAX : DateTimeUtil.stringToLocalDate(endDate);
        LocalTime sTime = "".equals(startTime) || startTime == null ? LocalTime.MIN : DateTimeUtil.stringToLocalTime(startTime);
        LocalTime eTime = "".equals(endTime) || endTime == null ? LocalTime.MAX : DateTimeUtil.stringToLocalTime(endTime);

       return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()),AuthorizedUser.getCaloriesPerDay())
               .stream()
               .filter(u -> DateTimeUtil.isBetweenDateTime(u.getDateTime().toLocalDate(),sDate,eDate))
               .filter(u -> DateTimeUtil.isBetweenDateTime(u.getDateTime().toLocalTime(),sTime,eTime))
               .collect(Collectors.toList());
    }
}