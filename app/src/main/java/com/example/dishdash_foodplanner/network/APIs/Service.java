package com.example.dishdash_foodplanner.network.APIs;

import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Country;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.response.NetworkResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("list.php?c=list")
    Call<NetworkResponse<Category>> getCategoriesList();

    @GET("list.php?a=list")
    Call<NetworkResponse<Country>> getCountriesList();

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
}