package com.example.dishdash_foodplanner.tabs.details.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.dishdash_foodplanner.model.POJO.Meal;
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

    public void scheduleMeal(Meal meal, Calendar selectedDate) {
        // Set time to midnight (00:00:00) to strip time component
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);
        long timestamp = selectedDate.getTimeInMillis();
        Log.i(TAG, "scheduleMeal in details presenter: " + timestamp);

        // Insert meal with the adjusted date
        repository.scheduleMealForDate(meal, new Date(timestamp));
    }
}