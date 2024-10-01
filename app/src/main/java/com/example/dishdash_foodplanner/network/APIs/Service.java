package com.example.dishdash_foodplanner.network.APIs;

import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.response.CategoryResponse;
import com.example.dishdash_foodplanner.network.response.NetworkResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("categories.php")
    Call<CategoryResponse> getCategoriesList();

    @GET("list.php?a=list")
    Call<NetworkResponse<Area>> getCountriesList();

    @GET("list.php?i=list")
    Call<NetworkResponse<Ingredient>> getIngredientsList();

    @GET("random.php")
    Call<NetworkResponse<Meal>> getRandomMeal();

    @GET("filter.php")
    Call<NetworkResponse<Meal>> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<NetworkResponse<Meal>> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<NetworkResponse<Meal>> getMealsByArea(@Query("a") String area);

    @GET("lookup.php")
    Call<NetworkResponse<Meal>> getMealById(@Query("i") String mealId);

    @GET("search.php")
    Call<NetworkResponse<Meal>> searchMealsByName(@Query("s") String mealName);

    @GET("search.php")
    Call<NetworkResponse<Meal>> getMealsByFirstLetter(@Query("f") String firstLetter);

}