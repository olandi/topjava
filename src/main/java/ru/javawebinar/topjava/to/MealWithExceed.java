package ru.javawebinar.topjava.to;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealWithExceed extends BaseTo {
    @NotNull
    private  LocalDateTime dateTime;
    @NotBlank
    @Size(max = 30)
    private  String description;

    @NotNull(message = "value must between 10 and 5000 " )
    @DecimalMin(value = "10")
    @DecimalMax(value = "5000")
    private  int calories;

    private  boolean exceed;

    public MealWithExceed(){}
    public MealWithExceed(Integer id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

   /* public void setDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime);
    }*/
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setExceed(boolean exceed) {
        this.exceed = exceed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealWithExceed that = (MealWithExceed) o;
        return calories == that.calories &&
                exceed == that.exceed &&
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, calories, exceed);
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}