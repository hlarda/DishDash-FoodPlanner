package com.example.dishdash_foodplanner.tabs.home.presenter;

import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.home.view.HomeView;
import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter {
    private final HomeView view;
    private final Repository repository;
    private List<Area> allAreas = new ArrayList<>();
    private List<Category> allCategories = new ArrayList<>();
    private List<Ingredient> allIngredients = new ArrayList<>();

    public HomePresenter(HomeView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadAreas() {
        repository.getAreas(new AppNetworkCallback<Area>() {
            @Override
            public void onSuccess(List<Area> response) {
                allAreas.clear();
                allAreas.addAll(response);
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
                allCategories.clear();
                allCategories.addAll(response);
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
                allIngredients.clear();
                allIngredients.addAll(response);
                view.showIngredients(response);
            }

            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }

    public void filterAreas(String query) {
        List<Area> filteredList = new ArrayList<>();
        for (Area area : allAreas) {
            if (area.strArea.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(area);
            }
        }
        view.showAreas(filteredList);
    }

    public void filterCategories(String query) {
        List<Category> filteredList = new ArrayList<>();
        for (Category category : allCategories) {
            if (category.strCategory.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }
        view.showCategories(filteredList);
    }

    public void filterIngredients(String query) {
        List<Ingredient> filteredList = new ArrayList<>();
        for (Ingredient ingredient : allIngredients) {
            if (ingredient.strIngredient.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ingredient);
            }
        }
        view.showIngredients(filteredList);
    }
}