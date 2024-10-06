package com.example.dishdash_foodplanner.tabs.details.presenter;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DetailsPresenter {
    private DetailsView view;
    private final Repository repository;

    public DetailsPresenter(DetailsView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadMealDetails(String mealId) {
        repository.getMealById(mealId, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> items) {
                view.showMealDetails(items.get(0));
            }

            @Override
            public void onFailure(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }

    public void saveMeal(Meal meal) {
        repository.insertSavedMeal(meal);
    }

    public void loadSavedMeal(String mealId) {
        repository.getSavedMeal(mealId, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (!meals.isEmpty()) {
                    view.showMealDetails(meals.get(0));
                } else {
                    view.showError("Meal not found");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }

    public void loadPlannedMeal(String mealId) {
        repository.getPlannedMeal(mealId, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (!meals.isEmpty()) {
                    view.showMealDetails(meals.get(0));
                } else {
                    view.showError("Meal not found");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }

    public void scheduleMeal(Meal meal, Date selectedDate) {
        MealPlan mealPlan = new MealPlan(
                selectedDate, meal.idMeal, meal.strMeal,
                meal.strCategory, meal.strArea,
                meal.strInstructions,
                meal.strMealThumb,
                meal.strTags,
                meal.strYoutube,
                meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4, meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9, meal.strIngredient10,
                meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5, meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10
        );
        repository.scheduleMealForDate(mealPlan);
    }

    public void addMealToCalendar(Context context, String title, String description, long startTime, long endTime) {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startTime);
        values.put(CalendarContract.Events.DTEND, endTime);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

        Uri uri = context.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        if (uri != null) {
            long eventId = Long.parseLong(uri.getLastPathSegment());
        }
    }
}