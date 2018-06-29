package ru.javawebinar.topjava.web.meal;


import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping("/ajax/admin/meals")
public class MealAjaxController extends AbstractMealController {

    @Override
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }
/*
    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (user.isNew()) {
            super.create(user);
        }
    }
 */

    @PostMapping
    public Meal create(/*@RequestParam("date") LocalDateTime dateTime,*/
          /*  @RequestParam("date") LocalDate date,
            @RequestParam("time") LocalTime time,*/
                       @RequestParam("description") String description,
                       @RequestParam("calories") Integer calories) {

        Meal meal = new Meal(/*LocalDateTime.of(date,time)*/LocalDateTime.now(), description, calories);

        return super.create(meal);
    }

    @Override
    public void update(Meal meal, int id) {
        super.update(meal, id);
    }



    @PostMapping(value = "/filter")
   /* public List<MealWithExceed> getBetween(
           LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime) {

        parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        return super.getBetween(startDate, startTime, endDate, endTime);
    }*/
    public List<MealWithExceed> getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
