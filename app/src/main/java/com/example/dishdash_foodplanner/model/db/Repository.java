package com.example.dishdash_foodplanner.model.db;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
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
    public void scheduleMealForDate(Meal meal, Date date) {
        Date strippedDate = stripTimeFromDate(date);
        Log.d(TAG, "Scheduling meal: " + meal.strMeal + " on date: " + strippedDate);

        executorService.execute(() -> {
            MealPlan plan = new MealPlan(meal, strippedDate);
            mealPlanDAO.insert(plan);
            Log.d(TAG, "MealPlan inserted for date: " + strippedDate);
        });
    }
    private Date stripTimeFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public LiveData<List<MealPlan>> getPlansForDate(Date selectedDate) {
        String dateString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate);
        Log.d(TAG, "Querying meal plans for date: " + dateString);
        return mealPlanDAO.getMealPlansForDate(dateString);
    }

    public void deleteMealPlan(Meal meal, Date selectedDate) {
        executorService.execute(() -> {
            MealPlan plan = new MealPlan(meal, stripTimeFromDate(selectedDate));
            Log.d(TAG, "Repo MealPlan: " + plan);
            mealPlanDAO.deleteMealPlan(plan);
        });
    }
}