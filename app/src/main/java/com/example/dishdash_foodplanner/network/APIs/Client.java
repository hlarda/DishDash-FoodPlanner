package com.example.dishdash_foodplanner.network.APIs;

import androidx.annotation.NonNull;

import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Country;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;
import com.example.dishdash_foodplanner.network.response.NetworkResponse;

import retrofit2.Call;
import retrofit2.Callback;
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

    public void getCategoriesList(AppNetworkCallback<Category> callback) {
        Call<NetworkResponse<Category>> call = service.getCategoriesList();
        call.enqueue(new Callback<NetworkResponse<Category>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Category>> call, @NonNull retrofit2.Response<NetworkResponse<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get categories");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Category>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getIngredientsList(AppNetworkCallback<Ingredient> callback) {
        Call<NetworkResponse<Ingredient>> call = service.getIngredientsList();
        call.enqueue(new Callback<NetworkResponse<Ingredient>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Ingredient>> call, @NonNull retrofit2.Response<NetworkResponse<Ingredient>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get ingredients");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Ingredient>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getCountriesList(AppNetworkCallback<Country> callback) {
        Call<NetworkResponse<Country>> call = service.getCountriesList();
        call.enqueue(new Callback<NetworkResponse<Country>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Country>> call, @NonNull retrofit2.Response<NetworkResponse<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get countries");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Country>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getRandomMeal(AppNetworkCallback<Meal> callback) {
        Call<NetworkResponse<Meal>> call = service.getRandomMeal();
        call.enqueue(new Callback<NetworkResponse<Meal>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Meal>> call, @NonNull retrofit2.Response<NetworkResponse<Meal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get random meal");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Meal>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getMealsByCategory(String category, AppNetworkCallback<Meal> callback) {
        Call<NetworkResponse<Meal>> call = service.getMealsByCategory(category);
        call.enqueue(new Callback<NetworkResponse<Meal>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Meal>> call, @NonNull retrofit2.Response<NetworkResponse<Meal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get meals by category");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Meal>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getMealsByIngredient(String ingredient, AppNetworkCallback<Meal> callback) {
        Call<NetworkResponse<Meal>> call = service.getMealsByIngredient(ingredient);
        call.enqueue(new Callback<NetworkResponse<Meal>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Meal>> call, @NonNull retrofit2.Response<NetworkResponse<Meal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get meals by ingredient");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Meal>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    public void getMealsByArea(String area, AppNetworkCallback<Meal> callback) {
        Call<NetworkResponse<Meal>> call = service.getMealsByArea(area);
        call.enqueue(new Callback<NetworkResponse<Meal>>() {
            @Override
            public void onResponse(@NonNull Call<NetworkResponse<Meal>> call, @NonNull retrofit2.Response<NetworkResponse<Meal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().meals);
                } else {
                    callback.onFailure("Failed to get meals by area");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkResponse<Meal>> call, @NonNull Throwable throwable) {
                callback.onFailure(throwable.getMessage());
            }
        });
    }
}