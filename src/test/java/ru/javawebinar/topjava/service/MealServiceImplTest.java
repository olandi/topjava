package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(100004, UserTestData.USER_ID);
        assertMatch(meal, MealTestData.DB_MEAL_3);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1,UserTestData.USER_ID);
    }
    @Test(expected = NotFoundException.class)
    public void getNotFoundByNotAuthorisedUser() throws Exception {
        service.get(100004,1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(100004, UserTestData.USER_ID);
        assertMatch(service.getAll(UserTestData.USER_ID), MealTestData.DB_MEAL_5,MealTestData.DB_MEAL_4,MealTestData.DB_MEAL_2,MealTestData.DB_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1,UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void DeleteByNotAuthorisedUser() throws Exception {
        service.delete(100004,1);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(
        service.getBetweenDateTimes(LocalDateTime.MIN,LocalDateTime.MAX,UserTestData.USER_ID)
        ,service.getAll(UserTestData.USER_ID));

    }
    @Test
    public void getBetweenDateTimes2() {
        assertMatch(
                service.getBetweenDateTimes
                        (LocalDateTime.parse("2012-05-16T15:36")
                                ,LocalDateTime.parse("2014-05-16T15:37")
                                ,UserTestData.USER_ID)
                ,MealTestData.DB_MEAL_4,MealTestData.DB_MEAL_3,MealTestData.DB_MEAL_2);

    }



    @Test
    public void getAllByAuthorisedUser() {
        List<Meal> all = service.getAll(AuthorizedUser.id());
        assertMatch(all,
                MealTestData.DB_MEAL_5,
                MealTestData.DB_MEAL_4 ,
                MealTestData.DB_MEAL_3 ,
                MealTestData.DB_MEAL_2 ,
                MealTestData.DB_MEAL_1);
    }

    @Test
    public void getAllByNotAuthorisedUser() {
        List<Meal> all = service.getAll(1);
        assertMatch(all);

    }



    @Test
    public void update() throws Exception {
        Meal updated = new Meal(100004, LocalDateTime.parse("2013-05-16T15:36"),"Ужин",345);
        updated.setDescription("UpdatedName");
        updated.setCalories(2000);
        service.update(updated,UserTestData.USER_ID);
        assertMatch(service.get(updated.getId(),USER_ID), updated);
    }


    @Test(expected = NotFoundException.class)
    public void updateByAnotherUser() throws Exception {
        Meal updated = new Meal(100004, LocalDateTime.parse("2013-05-16T15:36"),"Ужин",345);
        updated.setDescription("UpdatedName");
        updated.setCalories(2000);
        service.update(updated,1);
        assertMatch(service.get(updated.getId(),USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateByAnotherExistUser() throws Exception {
        Meal updated = new Meal(100004, LocalDateTime.parse("2013-05-16T15:36"),"Ужин",345);
        updated.setDescription("UpdatedName");
        updated.setCalories(2000);
        service.update(updated,ADMIN_ID);
        assertMatch(service.get(updated.getId(),USER_ID), updated);
    }


    @Test
    public void create() throws Exception {
        Meal newMeal =  new Meal(null, LocalDateTime.parse("2020-05-16T15:36"),"newЗавтрак",2000);
        Meal created = service.create(newMeal,UserTestData.USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(UserTestData.USER_ID),
                newMeal,
                MealTestData.DB_MEAL_5,
                MealTestData.DB_MEAL_4,
                MealTestData.DB_MEAL_3,
                MealTestData.DB_MEAL_2,
                MealTestData.DB_MEAL_1
                );
    }

    @Test(expected = DuplicateKeyException.class)
    public void DoubleTimeCreatePerUser() throws Exception {
        Meal newMealFirst =  new Meal(null, LocalDateTime.parse("2020-05-16T15:36"),"newЗавтракFirst",2000);
        Meal newMealSecond =  new Meal(null, LocalDateTime.parse("2020-05-16T15:36"),"newЗавтракSecond",20001);
        Meal createdFirst = service.create(newMealFirst,UserTestData.USER_ID);
        Meal createdSecond = service.create(newMealSecond,UserTestData.USER_ID);


    }

}