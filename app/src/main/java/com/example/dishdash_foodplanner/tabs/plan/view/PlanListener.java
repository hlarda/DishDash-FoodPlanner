package com.example.dishdash_foodplanner.tabs.plan.view;

import com.example.dishdash_foodplanner.model.POJO.Meal;

public interface PlanListener {
    void onRemoveFromPlanClicked(Meal meal);
    void onMealClicked(Meal meal);
}
