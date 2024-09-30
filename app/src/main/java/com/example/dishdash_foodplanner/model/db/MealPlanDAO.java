package com.example.dishdash_foodplanner.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dishdash_foodplanner.model.POJO.MealPlan;

import java.util.List;

@Dao
public interface MealPlanDAO {
    @Query("SELECT * FROM meal_plan_table")
    LiveData<List<MealPlan>> getMealPlans();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealPlan mealPlan);

    @Delete
    void deleteMealPlan(MealPlan mealPlan);

    @Query("SELECT * FROM meal_plan_table WHERE strftime('%Y-%m-%d', date / 1000, 'unixepoch') = :selectedDate")
    LiveData<List<MealPlan>> getMealPlansForDate(String selectedDate);
}