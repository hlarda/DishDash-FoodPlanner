package com.example.dishdash_foodplanner.network.interfaces;

import com.example.dishdash_foodplanner.model.POJO.Ingredient;

import java.util.List;

public interface IngredientCallback {
    public void onSuccessIngredients(List<Ingredient> ingredients);
    public void onFailureIngredients(String errorMsg);
}
