package com.example.dishdash_foodplanner.tabs.itemlist.presenter;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;
import com.example.dishdash_foodplanner.tabs.itemlist.view.ItemListView;

import java.util.List;

public class ItemListPresenter {
    private final ItemListView view;
    private final Repository repository;

    public ItemListPresenter(ItemListView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }
    public void loadMealsByCategory(String category) {
        repository.getMealsByCategory(category, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> response) {
                view.showMealList(response);
            }
            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }
    public void loadMealsByArea(String area) {
        repository.getMealsByArea(area, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> response) {
                view.showMealList(response);
            }
            @Override
            public void onFailure(String error) {
                view.showError(error);
            }
        });
    }
}
