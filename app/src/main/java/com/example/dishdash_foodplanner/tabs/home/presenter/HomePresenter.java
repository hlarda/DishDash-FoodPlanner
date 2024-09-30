package com.example.dishdash_foodplanner.tabs.home.presenter;

import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.home.view.HomeView;
import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;

import java.util.List;

public class HomePresenter {
    private final HomeView view;
    private final Repository repository;

    public HomePresenter(HomeView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadAreas() {
        repository.getAreas(new AppNetworkCallback<Area>() {
            @Override
            public void onSuccess(List<Area> response) {
                view.showAreas(response);
            }

            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }

    public void loadCategories() {
        repository.getCategories(new AppNetworkCallback<Category>() {
            @Override
            public void onSuccess(List<Category> response) {
                view.showCategories(response);
            }

            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }

    public void loadRandomMeals() {
        repository.getRandomMeals(10, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> response) {
                view.showRandomList(response);
            }

            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }

    public void loadIngredients() {
        repository.getIngredients(new AppNetworkCallback<Ingredient>() {
            @Override
            public void onSuccess(List<Ingredient> response) {
                view.showIngredients(response);
            }

            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }
}
