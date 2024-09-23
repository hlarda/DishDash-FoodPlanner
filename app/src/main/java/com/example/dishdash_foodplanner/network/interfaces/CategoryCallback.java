package com.example.dishdash_foodplanner.network.interfaces;

import com.example.dishdash_foodplanner.model.POJO.Category;

import java.util.List;

public interface CategoryCallback {
    public void onSuccessCategories(List<Category> categories);
    public void onFailureCategories(String errorMsg);
}