package com.example.dishdash_foodplanner.tabs.itemlist.presenter;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;
import com.example.dishdash_foodplanner.tabs.itemlist.view.ItemListView;

import java.util.List;

public class ItemListPresenter {
    private final ItemListView view;
    private final Client client;

    public ItemListPresenter(ItemListView view, Client client) {
        this.view = view;
        this.client = client;
    }
    public void loadMealsByCategory(String category) {
        client.getMealsByCategory(category, new AppNetworkCallback<Meal>() {
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
        client.getMealsByArea(area, new AppNetworkCallback<Meal>() {
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
