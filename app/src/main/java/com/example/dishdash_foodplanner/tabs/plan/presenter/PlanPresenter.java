package com.example.dishdash_foodplanner.tabs.plan.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.plan.view.PlanView;

import java.time.LocalDate;
import java.util.Calendar;
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
        // Create Calendar instance for the selected day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        LiveData<List<MealPlan>> plannedMeals = repository.getPlansForDate(startOfDay, endOfDay);
        plannedMeals.observeForever(new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlans) {
                if (mealPlans != null && !mealPlans.isEmpty()) {
                    view.showPlannedMeals(mealPlans);
                } else {
                    view.showError("No meals planned for this day");
                }
            }
        });
    }


    public void removeMealFromDate(MealPlan mealPlan) {
        repository.deleteMealPlan(mealPlan);
    }
}