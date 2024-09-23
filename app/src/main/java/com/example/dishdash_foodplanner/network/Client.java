package com.example.dishdash_foodplanner.network;


import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Country;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.response.CategoryResponse;
import com.example.dishdash_foodplanner.model.response.CountryResponse;
import com.example.dishdash_foodplanner.model.response.IngredientResponse;
import com.example.dishdash_foodplanner.network.interfaces.CategoryCallback;
import com.example.dishdash_foodplanner.network.interfaces.CountryCallback;
import com.example.dishdash_foodplanner.network.interfaces.IngredientCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final Service service;
    private static Client client;

    private Client(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }

    public static Client getInstance(){
        if (client == null){
            client = new Client();
        }
        return client;
    }

    public void getCategoriesList(CategoryCallback callback){
        Call<CategoryResponse> call = service.getCategoriesList();
        call.enqueue(new Callback<CategoryResponse>() {
                         @Override
                         public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                             if (response.isSuccessful() && response.body() != null) {
                                 List<Category> categories = response.body().meals;
                                 if (categories != null) {
                                     for (Category category : categories) {
                                         Log.i(TAG, "Category: " + category.strCategory);
                                     }
                                 } else {
                                     Log.i(TAG, "onResponse: Categories list is null");
                                 }
                             } else {
                                 Log.i(TAG, "onResponse: Failed to get categories");
                             }
                         }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void getIngredientsList(IngredientCallback callback){
        Call<IngredientResponse> call = service.getIngredientsList();
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(@NonNull Call<IngredientResponse> call, @NonNull Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredient> ingredients = response.body().meals;
                    if (ingredients != null) {
                        for (Ingredient ingredient : ingredients) {
                            Log.i(TAG, "Ingredient: " + ingredient.strIngredient);
                        }
                    } else {
                        Log.i(TAG, "onResponse: Ingredients list is null");
                    }
                } else {
                    Log.i(TAG, "onResponse: Failed to get ingredients");
                }
            }

            @Override
            public void onFailure(@NonNull Call<IngredientResponse> call, @NonNull Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void getCountriesList(CountryCallback callback){
        Call<CountryResponse> call = service.getCountriesList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountryResponse> call, @NonNull Response<CountryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Country> countries = response.body().meals;
                    if (countries != null) {
                        for (Country country : countries) {
                            Log.i(TAG, "Country: " + country.strArea);
                        }
                    } else {
                        Log.i(TAG, "onResponse: Countries list is null");
                    }
                } else {
                    Log.i(TAG, "onResponse: Failed to get countries");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountryResponse> call, @NonNull Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }
}
