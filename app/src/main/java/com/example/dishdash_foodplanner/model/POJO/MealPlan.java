package com.example.dishdash_foodplanner.model.POJO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_plan_table")
public class MealPlan {
    @PrimaryKey
    public int id;

    public String dayOfWeek;
    public String mealId;
}
