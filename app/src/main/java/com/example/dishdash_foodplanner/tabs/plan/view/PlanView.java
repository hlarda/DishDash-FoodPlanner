package com.example.dishdash_foodplanner.tabs.plan.view;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;

import java.util.List;

public interface PlanView {
    void showPlannedMeals(List<MealPlan> meals);
    void showError(String error);
}
