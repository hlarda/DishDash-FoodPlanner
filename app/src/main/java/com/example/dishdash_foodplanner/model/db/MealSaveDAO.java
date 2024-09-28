package com.example.dishdash_foodplanner.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dishdash_foodplanner.model.POJO.Meal;

import java.util.List;

@Dao
public interface MealSaveDAO {
    @Query("SELECT * FROM fav_meals_table")
    LiveData<List<Meal>> getMeals();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Meal Meal);

    @Delete
    void deleteMeal(Meal Meal);
}
