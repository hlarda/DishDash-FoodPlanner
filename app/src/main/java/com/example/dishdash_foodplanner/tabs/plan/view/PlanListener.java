package com.example.dishdash_foodplanner.tabs.plan.view;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;

public interface PlanListener {
    void onRemoveFromPlanClicked(MealPlan mealPlan);
    void onMealClicked(MealPlan mealPlan);
}
