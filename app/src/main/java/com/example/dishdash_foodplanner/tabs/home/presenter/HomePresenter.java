package com.example.dishdash_foodplanner.tabs.home.presenter;

import com.example.dishdash_foodplanner.tabs.home.view.HomeView;
import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;

import java.util.List;

public class HomePresenter {
    private final HomeView view;
    private final Client client;

    public HomePresenter(HomeView view, Client client) {
        this.view = view;
        this.client = client;
    }

    public void loadAreas() {
        client.getCountriesList(new AppNetworkCallback<Area>() {
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
        client.getCategoriesList(new AppNetworkCallback<Category>() {
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
        client.getRandomMeals(10,new AppNetworkCallback<Meal>() {
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
}