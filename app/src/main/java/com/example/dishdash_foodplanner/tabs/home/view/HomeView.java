package com.example.dishdash_foodplanner.tabs.home.view;

import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;

import java.util.List;

public interface HomeView {
    void showAreas(List<Area> areas);
    void showCategories(List<Category> categories);
    void showRandomList(List<Meal> randomList);
    void showError(String error);
}