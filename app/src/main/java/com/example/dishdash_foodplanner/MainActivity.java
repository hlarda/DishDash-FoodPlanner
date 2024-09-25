package com.example.dishdash_foodplanner;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Country;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.APIs.NetworkUtils;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimation);
        lottieAnimationView.setImageAssetsFolder("images/");

        getStartedBtn = findViewById(R.id.startButton);
        getStartedBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
        });

        if (NetworkUtils.isNetworkAvailable(this)) {
//            fetchCategories();
//            fetchIngredients();
//            fetchCountries();
//            fetchRandomMeal();
//            getMealsByCategory();
//            getMealsByIngredient();
//            getMealsByArea();
        } else {
            Log.e(TAG, "Network is not available");
        }
    }

    private void fetchCategories() {
        Client.getInstance().getCategoriesList(new AppNetworkCallback<Category>() {
            @Override
            public void onSuccess(List<Category> categories) {
                for (Category category : categories) {
                    Log.i(TAG, "Category: " + category.strCategory);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to fetch categories: " + errorMsg);
            }
        });
    }

    private void fetchIngredients() {
        Client.getInstance().getIngredientsList(new AppNetworkCallback<Ingredient>() {
            @Override
            public void onSuccess(List<Ingredient> ingredients) {
                for (Ingredient ingredient : ingredients) {
                    Log.i(TAG, "Ingredient: " + ingredient.strIngredient);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to fetch ingredients: " + errorMsg);
            }
        });
    }

    private void fetchCountries() {
        Client.getInstance().getCountriesList(new AppNetworkCallback<Country>() {
            @Override
            public void onSuccess(List<Country> countries) {
                for (Country country : countries) {
                    Log.i(TAG, "Country: " + country.strArea);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to fetch countries: " + errorMsg);
            }
        });
    }

    private void fetchRandomMeal() {
        Client.getInstance().getRandomMeal(new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                for (Meal meal : meals) {
                    Log.i(TAG, "Meal: " + meal.idMeal);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to fetch meals: " + errorMsg);
            }
        });
    }

    private void getMealsByCategory() {
        Client.getInstance().getMealsByCategory("Seafood", new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (meals != null && !meals.isEmpty()) {
                    for (Meal meal : meals) {
                        Log.i(TAG, "Meal by Category: " + meal.strMeal);
                    }
                } else {
                    Log.i(TAG, "No meals found for category: " + "Seafood");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to get meals by category: " + errorMsg);
            }
        });
    }

    private void getMealsByIngredient() {
        Client.getInstance().getMealsByIngredient("chicken_breast", new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (meals != null && !meals.isEmpty()) {
                    for (Meal meal : meals) {
                        Log.i(TAG, "Meal by Ingredient: " + meal.strMeal);
                    }
                } else {
                    Log.i(TAG, "No meals found for ingredient: " + "chicken_breast");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to get meals by ingredient: " + errorMsg);
            }
        });
    }

    private void getMealsByArea() {
        Client.getInstance().getMealsByArea("Canadian", new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (meals != null && !meals.isEmpty()) {
                    for (Meal meal : meals) {
                        Log.i(TAG, "Meal by Area: " + meal.strMeal);
                    }
                } else {
                    Log.i(TAG, "No meals found for area: " + "Canadian");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e(TAG, "Failed to get meals by area: " + errorMsg);
            }
        });
    }
}