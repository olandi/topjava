package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
   // private static final Sort SORT_DATE = new Sort(Sort.Direction.DESC,  "dateTime");
    @Autowired
    private CrudMealRepository crudRepository;
@Transactional
    @Override
    public Meal save(Meal meal, int userId) {

        if (!meal.isNew())
            crudRepository.update(meal.getDateTime(),meal.getCalories(),meal.getDescription(),meal.getId(),userId);
        else{
            meal.setUser(new User(100000, "Admin", "admin@gmail.com", "admin", Role.ROLE_USER));
            crudRepository.save(meal);
            crudRepository.update(meal.getDateTime(),meal.getCalories(),meal.getDescription(),meal.getId(),userId);
        }
        return get(meal.getId(),userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteMealByIdAndUserId(id, userId) !=0 ;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getMealByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
      //  return crudRepository.getAllByUserId(userId).stream().;
       // return crudRepository.findAll(SORT_DATE);
        return crudRepository.getAllByUserIdOrderByDateTimeDesc(userId);
    }




    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBetween(startDate,endDate,userId);
    }
}
