package com.example.dishdash_foodplanner.model.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdash_foodplanner.model.POJO.MealPlan;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealPlanRepository {
    private final MealPlanDAO mealPlanDAO;
    private final ExecutorService executorService;

    public MealPlanRepository(Context context) {
        MealDatabase db = MealDatabase.getInstance(context);
        mealPlanDAO = db.getMealPlanDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<MealPlan>> getMealPlans() {
        return mealPlanDAO.getMealPlans();
    }

    public void insert(MealPlan mealPlan) {
        executorService.execute(() -> mealPlanDAO.insert(mealPlan));
    }

    public void deleteMealPlan(MealPlan mealPlan) {
        executorService.execute(() -> mealPlanDAO.deleteMealPlan(mealPlan));
    }
}