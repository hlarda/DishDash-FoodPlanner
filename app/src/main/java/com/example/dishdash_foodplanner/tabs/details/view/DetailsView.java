package com.example.dishdash_foodplanner.tabs.details.view;

import com.example.dishdash_foodplanner.model.POJO.Meal;

public interface DetailsView {
    void showMealDetails(Meal meal);
    void showError(String error);
}