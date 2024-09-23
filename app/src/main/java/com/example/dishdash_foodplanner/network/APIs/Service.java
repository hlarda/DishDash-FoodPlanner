package com.example.dishdash_foodplanner.network.APIs;


import com.example.dishdash_foodplanner.model.response.CategoryResponse;
import com.example.dishdash_foodplanner.model.response.CountryResponse;
import com.example.dishdash_foodplanner.model.response.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("list.php?c=list")
    Call<CategoryResponse> getCategoriesList();

    @GET("list.php?a=list")
    Call<CountryResponse> getCountriesList();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredientsList();

}
