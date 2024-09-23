package com.example.dishdash_foodplanner.network.APIs;


import com.example.dishdash_foodplanner.model.response.CategoryResponse;
import com.example.dishdash_foodplanner.model.response.CountryResponse;
import com.example.dishdash_foodplanner.model.response.IngredientResponse;
import com.example.dishdash_foodplanner.model.response.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("list.php?c=list")
    Call<CategoryResponse> getCategoriesList();

    @GET("list.php?a=list")
    Call<CountryResponse> getCountriesList();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredientsList();

    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);
}
