package com.example.dishdash_foodplanner.tabs.saved.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.saved.view.SavedView;

import java.util.List;

public class SavedPresenter {
    private final SavedView view;
    private final Repository repository;

    public SavedPresenter(SavedView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadSavedMeals() {
        LiveData<List<Meal>> savedList = repository.getSavedMeals();
        savedList.observeForever(new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                view.showSavedMeals(meals);
            }
        });
    }

    public void removeFromSaved(Meal meal) {
        repository.deleteSavedMeal(meal);
    }
}
