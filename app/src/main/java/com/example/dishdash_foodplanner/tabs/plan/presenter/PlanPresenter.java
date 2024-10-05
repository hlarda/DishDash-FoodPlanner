package com.example.dishdash_foodplanner.tabs.plan.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.plan.view.PlanView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanPresenter {
    private final PlanView view;
    private final Repository repository;
    private final LifecycleOwner lifecycleOwner;
    private final MutableLiveData<Date> selectedDate = new MutableLiveData<>();
    private LiveData<List<MealPlan>> plannedMeals;

    public PlanPresenter(PlanView view, Repository repository, LifecycleOwner lifecycleOwner) {
        this.view = view;
        this.repository = repository;
        this.lifecycleOwner = lifecycleOwner;

        selectedDate.observe(lifecycleOwner, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                loadPlansForDate(date);
            }
        });
    }

    public void setSelectedDate(Date date) {
        selectedDate.setValue(date);
    }

    public void loadPlansForDate(Date date) {
        if (date == null) {
            Log.e(TAG, "loadPlansForDate: date must not be null");
            return;
        }

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

        if (plannedMeals != null) {
            plannedMeals.removeObservers(lifecycleOwner);
        }

        plannedMeals = repository.getPlansForDate(startOfDay, endOfDay);
        plannedMeals.observe(lifecycleOwner, new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlans) {
                if (mealPlans == null || mealPlans.isEmpty()) {
                    view.showPlannedMeals(new ArrayList<>());  // Pass an empty list if no plans are found
                } else {
                    view.showPlannedMeals(mealPlans);
                }
            }
        });
    }

    public void removeMealFromDate(MealPlan mealPlan) {
        repository.deleteMealPlan(mealPlan);
        // Reload plans for the current selected date
        Date date = selectedDate.getValue();
        if (date != null) {
            loadPlansForDate(date);
        } else {
            Log.e(TAG, "removeMealFromDate: selectedDate is null");
        }
    }
}