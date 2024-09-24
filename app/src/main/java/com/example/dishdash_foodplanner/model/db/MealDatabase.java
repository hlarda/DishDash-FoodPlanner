package com.example.dishdash_foodplanner.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;

@Database(entities = {Meal.class, MealPlan.class},version = 1)
public abstract class MealDatabase extends RoomDatabase {
    private static MealDatabase instance = null;
    public abstract MealDAO getMealDAO();
    public abstract MealPlanDAO getMealPlanDAO();

    public static synchronized MealDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MealDatabase.class, "productsDb")
                    .build();
        }
        return instance;
    }
}
