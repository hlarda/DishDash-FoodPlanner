package com.example.dishdash_foodplanner.model.POJO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "meal_plan_table")
public class MealPlan {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String mealId;
    public String mealName;
    public String imageUrl;
    public String category;
    public String area;
    public Date date;

    // Empty constructor
    public MealPlan() {}

    // Constructor with parameters matching the fields
    public MealPlan(int id, String mealId, String mealName, String imageUrl, String category, String area, Date date) {
        this.id = id;
        this.mealId = mealId;
        this.mealName = mealName;
        this.imageUrl = imageUrl;
        this.category = category;
        this.area = area;
        this.date = date;
    }

    // Constructor using Meal object
    public MealPlan(Meal meal, Date date) {
        this.mealId = meal.idMeal;
        this.mealName = meal.strMeal;
        this.imageUrl = meal.strMealThumb;
        this.category = meal.strCategory;
        this.area = meal.strArea;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealPlan mealPlan = (MealPlan) o;

        return id == mealPlan.id &&
                Objects.equals(mealId, mealPlan.mealId) &&
                Objects.equals(mealName, mealPlan.mealName) &&
                Objects.equals(imageUrl, mealPlan.imageUrl) &&
                Objects.equals(category, mealPlan.category) &&
                Objects.equals(area, mealPlan.area) &&
                Objects.equals(date, mealPlan.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mealId, mealName, imageUrl, category, area, date);
    }
}