package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.CrudImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {

    private CrudImpl repository = new CrudImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action") == null ? "" : req.getParameter("action");


        if (action.equals("delete")) {
            repository.deleteById(Integer.parseInt(req.getParameter("id")));
        }

        if (action.equals("update")) {
            int id = Integer.parseInt(req.getParameter("id"));

            Meal meal = repository.getById(id);

            req.setAttribute("meal", meal);

            RequestDispatcher rd = req.getRequestDispatcher("views/addMeal.jsp");
            rd.forward(req, resp);
        }

        if (action.equals("create")) {
            RequestDispatcher rd = req.getRequestDispatcher("views/addMeal.jsp");
            rd.forward(req, resp);

        }

        req.setAttribute("meals",
                MealsUtil.getFilteredWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000)
        );
        RequestDispatcher rd = req.getRequestDispatcher("views/meals.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        Integer calories = Integer.parseInt(req.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);

        if (id != null && !id.isEmpty()) {
            meal.setId(Integer.parseInt(id));
            repository.update(meal);
        } else {

            repository.update(meal);
        }


        resp.sendRedirect("meals");

    }
}
