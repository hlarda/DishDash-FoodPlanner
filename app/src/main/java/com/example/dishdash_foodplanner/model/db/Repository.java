package com.example.dishdash_foodplanner.model.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final MealSaveDAO mealSaveDAO;
    private final MealPlanDAO mealPlanDAO;
    private final Client client;
    private final ExecutorService executorService;

    public Repository(Context context) {
        MealDatabase db = MealDatabase.getInstance(context);
        mealSaveDAO = db.getMealDAO();
        mealPlanDAO = db.getMealPlanDAO();
        client = new Client();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Meal>> getSavedMeals() {
        return mealSaveDAO.getMeals();
    }

    public void insertSavedMeal(Meal meal) {
        executorService.execute(() -> mealSaveDAO.insert(meal));
    }

    public void deleteSavedMeal(Meal meal) {
        executorService.execute(() -> mealSaveDAO.deleteMeal(meal));
    }

    public LiveData<List<MealPlan>> getMealPlans() {
        return mealPlanDAO.getMealPlans();
    }

    public void insertMealPlan(MealPlan mealPlan) {
        executorService.execute(() -> mealPlanDAO.insert(mealPlan));
    }

    public void deleteMealPlan(MealPlan mealPlan) {
        executorService.execute(() -> mealPlanDAO.deleteMealPlan(mealPlan));
    }
    public void getAreas(AppNetworkCallback<Area> callback) {
        client.getCountriesList(callback);
    }

    public void getCategories(AppNetworkCallback<Category> callback) {
        client.getCategoriesList(callback);
    }

    public void getRandomMeals(int count, AppNetworkCallback<Meal> callback) {
        client.getRandomMeals(count, callback);
    }
    public void getMealById(String mealId, AppNetworkCallback<Meal> callback) {
        client.getMealById(mealId, callback);
    }
    public void getMealsByCategory(String category, AppNetworkCallback<Meal> callback) {
        client.getMealsByCategory(category, callback);
    }
    public void getMealsByArea(String area, AppNetworkCallback<Meal> callback) {
        client.getMealsByArea(area, callback);
    }
}