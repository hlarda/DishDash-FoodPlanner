package com.example.dishdash_foodplanner.network.APIs;

import androidx.annotation.NonNull;

import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Country;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.response.CategoryResponse;
import com.example.dishdash_foodplanner.network.response.CountryResponse;
import com.example.dishdash_foodplanner.network.response.IngredientResponse;
import com.example.dishdash_foodplanner.network.response.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final Service service;
    private static Client client;

    private Client() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public void getCategoriesList(NetworkCallback<Category> callback) {
        Call<CategoryResponse> call = service.getCategoriesList();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get categories");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getIngredientsList(NetworkCallback<Ingredient> callback) {
        Call<IngredientResponse> call = service.getIngredientsList();
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(@NonNull Call<IngredientResponse> call, @NonNull Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get ingredients");
                }
            }

            @Override
            public void onFailure(@NonNull Call<IngredientResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getCountriesList(NetworkCallback<Country> callback) {
        Call<CountryResponse> call = service.getCountriesList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountryResponse> call, @NonNull Response<CountryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get countries");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountryResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getRandomMeal(NetworkCallback<Meal> callback) {
        Call<MealResponse> call = service.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get random meal");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getMealsByCategory(String category, NetworkCallback<Meal> callback) {
        Call<MealResponse> call = service.getMealsByCategory(category);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get meals by category");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getMealsByIngredient(String ingredient, NetworkCallback<Meal> callback) {
        Call<MealResponse> call = service.getMealsByIngredient(ingredient);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get meals by ingredient");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getMealsByArea(String area, NetworkCallback<Meal> callback) {
        Call<MealResponse> call = service.getMealsByArea(area);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get meals by area");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }
}