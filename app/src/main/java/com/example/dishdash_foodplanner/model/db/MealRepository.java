package com.example.dishdash_foodplanner.model.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdash_foodplanner.model.POJO.Meal;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealRepository {
    private final MealDAO mealDAO;
    private final ExecutorService executorService;

    public MealRepository(Context context) {
        MealDatabase db = MealDatabase.getInstance(context);
        mealDAO = db.getMealDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Meal>> getMeals() {
        return mealDAO.getMeals();
    }

    public void insert(Meal meal) {
        executorService.execute(() -> mealDAO.insert(meal));
    }

    public void deleteMeal(Meal meal) {
        executorService.execute(() -> mealDAO.deleteMeal(meal));
    }
}
