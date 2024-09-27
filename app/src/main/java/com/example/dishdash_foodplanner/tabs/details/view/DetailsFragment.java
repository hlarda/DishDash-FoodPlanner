package com.example.dishdash_foodplanner.tabs.details.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.tabs.details.presenter.DetailsPresenter;

public class DetailsFragment extends Fragment implements DetailsView {

    private DetailsPresenter presenter;
    private TextView title, categoryArea, ingredientsList, instructions;
    private WebView videoView;
    private ImageView thumbnail;
    private ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        title = view.findViewById(R.id.title);
        categoryArea = view.findViewById(R.id.category_area);
        ingredientsList = view.findViewById(R.id.ingredients_list);
        instructions = view.findViewById(R.id.instructions);
        videoView = view.findViewById(R.id.videoView);
        thumbnail = view.findViewById(R.id.thumbnail);
        backBtn = view.findViewById(R.id.backBtn);

        presenter = new DetailsPresenter(this, new Client());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String mealId = bundle.getString("mealId");
            if (mealId != null) {
                presenter.loadMealDetails(mealId);
            }
        }

        backBtn.setOnClickListener(v -> getActivity().onBackPressed());

        return view;
    }

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    public void showMealDetails(Meal meal) {
        Glide.with(getContext()).load(meal.strMealThumb)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnail);

        title.setText(meal.strMeal);
        categoryArea.setText(meal.strCategory + " | " + meal.strArea);

        StringBuilder ingredients = new StringBuilder();
        appendIngredient(ingredients, meal.strIngredient1, meal.strMeasure1);
        appendIngredient(ingredients, meal.strIngredient2, meal.strMeasure2);
        appendIngredient(ingredients, meal.strIngredient3, meal.strMeasure3);
        appendIngredient(ingredients, meal.strIngredient4, meal.strMeasure4);
        appendIngredient(ingredients, meal.strIngredient5, meal.strMeasure5);
        appendIngredient(ingredients, meal.strIngredient6, meal.strMeasure6);
        appendIngredient(ingredients, meal.strIngredient7, meal.strMeasure7);
        appendIngredient(ingredients, meal.strIngredient8, meal.strMeasure8);
        appendIngredient(ingredients, meal.strIngredient9, meal.strMeasure9);
        appendIngredient(ingredients, meal.strIngredient10, meal.strMeasure10);
        appendIngredient(ingredients, meal.strIngredient11, meal.strMeasure11);
        appendIngredient(ingredients, meal.strIngredient12, meal.strMeasure12);
        appendIngredient(ingredients, meal.strIngredient13, meal.strMeasure13);
        appendIngredient(ingredients, meal.strIngredient14, meal.strMeasure14);
        appendIngredient(ingredients, meal.strIngredient15, meal.strMeasure15);

        ingredientsList.setText(ingredients.toString());
        instructions.setText(meal.strInstructions);

        videoView.getSettings().setJavaScriptEnabled(true);
        String videoUrl = meal.strYoutube.replace("watch?v=", "embed/");
        videoView.loadUrl(videoUrl);
    }

    private void appendIngredient(StringBuilder ingredients, String ingredient, String measure) {
        if (ingredient != null && !ingredient.isEmpty()) {
            ingredients.append(ingredient);
            if (measure != null && !measure.isEmpty()) {
                ingredients.append(" - ").append(measure);
            }
            ingredients.append("\n");
        }
    }

    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
    }
}