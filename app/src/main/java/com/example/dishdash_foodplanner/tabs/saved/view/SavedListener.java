package com.example.dishdash_foodplanner.tabs.saved.view;

import com.example.dishdash_foodplanner.model.POJO.Meal;

public interface SavedListener {
    void onRemoveFromSavedClicked(Meal meal);
    void onMealClicked(Meal meal);
}
