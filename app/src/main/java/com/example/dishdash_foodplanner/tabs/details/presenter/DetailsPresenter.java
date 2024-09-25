package com.example.dishdash_foodplanner.tabs.details.presenter;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsView;

import java.util.List;

public class DetailsPresenter {
    private DetailsView view;
    private Client client;

    public DetailsPresenter(DetailsView view, Client client) {
        this.view = view;
        this.client = client;
    }

    public void loadMealDetails(String mealId) {
        client.getMealById(mealId, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> items) {
                view.showMealDetails(items.get(0));
            }

            @Override
            public void onFailure(String errorMsg) {
                view.showError(errorMsg);
            }
        });
    }
}