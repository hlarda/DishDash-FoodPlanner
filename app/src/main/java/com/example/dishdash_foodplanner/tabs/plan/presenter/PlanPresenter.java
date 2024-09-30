package com.example.dishdash_foodplanner.tabs.plan.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.plan.view.PlanView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PlanPresenter {
    private final PlanView view;
    private final Repository repository;
    private final LifecycleOwner lifecycleOwner;

    public PlanPresenter(PlanView view, Repository repository, LifecycleOwner lifecycleOwner) {
        this.view = view;
        this.repository = repository;
        this.lifecycleOwner = lifecycleOwner;
    }

    public void loadPlansForDate(Date date) {
        repository.getPlansForDate(date).observe(lifecycleOwner, mealPlans -> {
            Log.d(TAG, "Meal plans for date: " + date + " -> " + mealPlans);
            if (mealPlans != null && !mealPlans.isEmpty()) {

                view.showPlannedMeals(mealPlans);
            } else {
                view.showError("No meals planned for this date.");
            }
        });
    }


    public void removeMealFromDate(MealPlan mealPlan) {
        repository.deleteMealPlan(mealPlan);
    }
}