package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
       // Meal_LIST.forEach(this::save);
        int j = 1;
        for (int i = 0;i<Meal_LIST.size();i++){

            save(Meal_LIST.get(i),j);
            //Meal_LIST.get(i).setUserId(j);
            if (i%3==2) ++j;
        }
    }
    /*
    0 1 2 3 4 5 6 7 8 9 10 11 12

    1 1 1 2 2 2 3 3 3 3 4  4  4
     */


    private static final List<Meal> Meal_LIST = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),

            new Meal(LocalDateTime.of(2015, Month.JUNE, 10, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 10, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 15, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.AUGUST, 5, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.AUGUST, 1, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.AUGUST, 28, 20, 0), "Ужин", 510)
    );

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());

            meal.setUserId(userId);

            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage


        return  //meal.getUserId()==userId?
                repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
                    meal.setUserId(userId);
                    return meal;
                })//TODO
             //   :
               // null
                ;
    }

    @Override
    public Meal delete(int id, Integer userId) {
        Meal meal = get(id,userId);
      //  if (meal != null) ;
        return meal != null? repository.remove(meal.getId()) : null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        return repository.values().stream()
                .filter(m -> m.getUserId()==userId&&m.getId()==id)
                .findFirst().orElse(null);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {/*
        return repository.values().stream().filter(m -> m.getUserId()==userId)
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());*/
        return getFiltered(LocalDate.MIN,LocalDate.MAX,LocalTime.MIN,LocalTime.MAX,userId);
    }

    public Collection<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer userId){
        return repository.values().stream()
                .filter(m -> m.getUserId()==userId)
                .filter(u -> DateTimeUtil.isBetweenDateTime(u.getDate(),startDate,endDate))
                .filter(u -> DateTimeUtil.isBetweenDateTime(u.getTime(),startTime,endTime))
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());

    }



    //for tests
    private Collection<Meal> getRepositoryMeal() {
        return repository.values().stream().sorted(Comparator.comparing(Meal::getDate)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        InMemoryMealRepositoryImpl mealRepository = new InMemoryMealRepositoryImpl();

        //System.out.println(mealRepository.getRepositoryMeal());
        System.out.println("All meals in repository\n"
        +mealRepository.getRepositoryMeal()+"\n");
/*
        System.out.println("----------------------GET-ALL------------------------------");
        System.out.println("The meals of authorised existed user\n"
                +mealRepository.getAll(AuthorizedUser.id())+"\n");

        System.out.println("The meals of authorised existed user\n"
                +mealRepository.getAll(2)+"\n");

        System.out.println("The meals of authorised not existed user\n"
                +mealRepository.getAll(155)+"\n");
        System.out.println("--------------------END--GET-ALL------------------------------\n");


        System.out.println("----------------------GET-------------------------------");
        System.out.println("Get meal of authorised existed user\n"
                +mealRepository.get(2,AuthorizedUser.id())+"\n");

        System.out.println("Get meal of other user\n"
                + mealRepository.get(12,AuthorizedUser.id())+"\n");

        System.out.println("Get not exist meal of authorised user\n"
                + mealRepository.get(155,AuthorizedUser.id())+"\n");

        System.out.println("Get not exist meal of authorised user\n"
                + mealRepository.get(1,155)+"\n");
        System.out.println("--------------------END--GET----------------------------\n");


        System.out.println("----------------------DELETE-------------------------------");

        System.out.println("Delete meal of authorised user\n" +
                mealRepository.delete(2,AuthorizedUser.id())+"\n"
                +mealRepository.getAll(AuthorizedUser.id())+"\n");


        System.out.println("Delete meal of unauthorised user\n" +
                mealRepository.delete(12,AuthorizedUser.id())+"\n"
                +mealRepository.getAll(AuthorizedUser.id())+"\n");


        System.out.println("Delete meal of unauthorised user\n" +
                mealRepository.delete(12,12414)+"\n"
                +mealRepository.getAll(AuthorizedUser.id())+"\n");

        System.out.println("--------------------END--DELETE---------------------------\n");
*/
        System.out.println("----------------------SAVE-------------------------------");

/*
        System.out.println("add new Meal by authorised user\n"+
        mealRepository.save(
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                AuthorizedUser.id())
        );
        System.out.println(mealRepository.getAll(AuthorizedUser.id()));
*/

        System.out.println("update  meal by authorised user\n"+
                mealRepository.save(
                        new Meal(5,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "aaaaa", 500000),
                        AuthorizedUser.id())
        );
        System.out.println(mealRepository.getAll(AuthorizedUser.id()));
        System.out.println(mealRepository.getAll(0));


/*
        System.out.println("update meal by not exist user\n"+
                mealRepository.save(
                        new Meal(1,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                        155)
        );
        System.out.println(mealRepository.getAll(28));*/


    }


}

