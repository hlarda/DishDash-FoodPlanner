package com.example.dishdash_foodplanner;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Country;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.network.APIs.NetworkUtils;
import com.example.dishdash_foodplanner.network.callbacks.CategoryCallback;
import com.example.dishdash_foodplanner.network.callbacks.CountryCallback;
import com.example.dishdash_foodplanner.network.callbacks.IngredientCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (NetworkUtils.isNetworkAvailable(this)) {
            fetchCategories();
            fetchIngredients();
            fetchCountries();
        } else {
            Log.e(TAG, "Network is not available");
        }
    }

    private void fetchCategories() {
        Client.getInstance().getCategoriesList(new CategoryCallback() {
            @Override
            public void onSuccessCategories(List<Category> categories) {
                for (Category category : categories) {
                    Log.i(TAG, "Category: " + category.strCategory);
                }
            }

            @Override
            public void onFailureCategories(String errorMsg) {
                Log.e(TAG, "Failed to fetch categories: " + errorMsg);
            }
        });
    }
    private void fetchIngredients() {
        Client.getInstance().getIngredientsList(new IngredientCallback() {
            @Override
            public void onSuccessIngredients(List<Ingredient> ingredients) {
                for (Ingredient ingredient : ingredients) {
                    Log.i(TAG, "Ingredient: " + ingredient.strIngredient);
                }
            }

            @Override
            public void onFailureIngredients(String errorMsg) {
                Log.e(TAG, "Failed to fetch ingredients: " + errorMsg);
            }
        });
    }
    private void fetchCountries() {
        Client.getInstance().getCountriesList(new CountryCallback() {
            @Override
            public void onSuccessCountries(List<Country> countries) {
                for (Country country : countries) {
                    Log.i(TAG, "Country: " + country.strArea);
                }
            }

            @Override
            public void onFailureCountries(String errorMsg) {
                Log.e(TAG, "Failed to fetch countries: " + errorMsg);
            }
        });
    }
}