package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            System.out.println("\n\n\n"+mealRestController.getAll());
            System.out.println(mealRestController.create(
                    new Meal(LocalDateTime.of(2015, Month.AUGUST, 28, 20, 0), "Ужин", 555)
            ));
            System.out.println("\n\n\n"+mealRestController.getAll());

            System.out.println(mealRestController.update(
                    new Meal(13,LocalDateTime.of(2015, Month.AUGUST, 28, 20, 0), "Ужин", 333),13
            ));

            System.out.println("\n\n\n"+mealRestController.getAll());
        }
    }
}
