package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    // null if updated meal do not belong to userId
   @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.dateTime = :datetime, m.calories= :calories, m.description=:description where m.id=:id and m.user.id=:userId")
    int update(@Param("datetime") LocalDateTime dateTime,@Param("calories")
           int calories,@Param("description") String description,@Param("id")  int id, @Param("userId")  int userId);


    // false if meal do not belong to userId
   // boolean delete(int id, int userId);
    @Transactional
    int deleteMealByIdAndUserId(int id, int userId);


    @Modifying
    @Query("SELECT m from Meal m WHERE m.id= :id AND m.user.id= :userId")
    Meal get(@Param("id") int id,@Param("userId") int userId);

    List<Meal> getAllByUserIdOrderByDateTimeDesc (int userId);


    // ORDERED dateTime desc
    @Query("SELECT m FROM Meal m " +
            "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,@Param("userId") int userId);



    Meal getMealByIdAndUserId (int id, int userId);

}
