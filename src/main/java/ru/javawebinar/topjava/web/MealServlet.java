package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    //private MealRepository mealRestController;
    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // mealRestController = new InMemoryMealRepositoryImpl();
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew())
            mealRestController.create(meal);
        else
            mealRestController.update(meal, meal.getId());

        response.sendRedirect("meals" + "?" + request.getParameter("filter"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user");
        if (userId != null) {
            AuthorizedUser.setId(Integer.parseInt(userId));
            response.sendRedirect("meals");
            return;
        }

        String sDate = request.getParameter("sDate");
        String eDate = request.getParameter("eDate");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");

        String filter = ("sDate=" + sDate + "&eDate=" + eDate + "&sTime=" + sTime + "&eTime=" + eTime)
                .replaceAll("null", "");

        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals" + "?" + filter);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:

                log.info("getAll");
                request.setAttribute("meals", mealRestController.getFiltered(/*
                        "".equals(sDate) || sDate == null ? LocalDate.MIN : DateTimeUtil.stringToLocalDate(sDate),
                        "".equals(eDate) || eDate == null ? LocalDate.MAX : DateTimeUtil.stringToLocalDate(eDate),
                        "".equals(sTime) || sTime == null ? LocalTime.MIN : DateTimeUtil.stringToLocalTime(sTime),
                        "".equals(eTime) || eTime == null ? LocalTime.MAX : DateTimeUtil.stringToLocalTime(eTime))*/
                        sDate, eDate, sTime, eTime)

                );

                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
