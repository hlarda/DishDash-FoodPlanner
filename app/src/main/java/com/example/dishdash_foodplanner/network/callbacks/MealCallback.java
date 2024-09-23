package com.example.dishdash_foodplanner.network.callbacks;

import com.example.dishdash_foodplanner.model.POJO.Meal;

import java.util.List;

public interface MealCallback {
    public void onSuccessMeals(List<Meal> meals);
    public void onFailureMeals(String errorMsg);
}
