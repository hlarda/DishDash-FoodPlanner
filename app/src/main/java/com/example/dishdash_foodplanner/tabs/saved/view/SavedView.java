package com.example.dishdash_foodplanner.tabs.saved.view;

import com.example.dishdash_foodplanner.model.POJO.Meal;

import java.util.List;

public interface SavedView {
    void showSavedMeals(List<Meal> meals);
    void showError(String error);
}
