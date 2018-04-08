package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
public class MealController {

    @Autowired
    private MealRestController mealController;

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String filter(HttpServletRequest request,Model model){
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }


    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        mealController.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String getAll(Model model){
        model.addAttribute("meals", mealController.getAll());
        return "meals";
    }


    @RequestMapping("/add")
    public String createMeal( Model model){
        model.addAttribute("meal",  new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "meal";
    }

    @RequestMapping("/edit/{id}")
    public String editMeal(@PathVariable("id") int id, Model model){
        model.addAttribute("meal", mealController.get(id));
        return "meal";
    }

    @RequestMapping(value= "/meal/add", method = RequestMethod.POST)
    public String addPerson(HttpServletRequest request){

        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        String id = request.getParameter("id");

        if (id.isEmpty()) {
            mealController.create(meal);
        } else {

            mealController.update(meal,  Integer.parseInt(id));
        }
        return "redirect:/meals";
    }
}