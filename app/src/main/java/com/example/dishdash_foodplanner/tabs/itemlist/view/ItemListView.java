package com.example.dishdash_foodplanner.tabs.itemlist.view;

import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;

import java.util.List;

public interface ItemListView {
    void showMealList(List<Meal> randomList);
    void showError(String error);
}
