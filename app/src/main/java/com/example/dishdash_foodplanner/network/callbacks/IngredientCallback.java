package com.example.dishdash_foodplanner.network.callbacks;

import com.example.dishdash_foodplanner.model.POJO.Ingredient;

import java.util.List;

public interface IngredientCallback {
    public void onSuccessIngredients(List<Ingredient> ingredients);
    public void onFailureIngredients(String errorMsg);
}
