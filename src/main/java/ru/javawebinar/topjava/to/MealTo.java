package ru.javawebinar.topjava.to;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
/*
public class MealTo extends BaseTo implements Serializable {
//хорошо бы разобраться с датами
    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    @Size(max = 30)
    private String description;

    @NotNull(message = "value must between 10 and 5000 " )
    @DecimalMin(value = "10")
    @DecimalMax(value = "5000")

    private Integer calories;

    public MealTo(){}
    public MealTo(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime( String dateTime) {

        this.dateTime = LocalDateTime.parse(dateTime);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}*/