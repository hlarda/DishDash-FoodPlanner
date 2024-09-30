package com.example.dishdash_foodplanner.tabs.details.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.tabs.details.presenter.DetailsPresenter;
import com.example.dishdash_foodplanner.tabs.home.presenter.HomePresenter;

import java.util.Calendar;
import java.util.Date;

public class DetailsFragment extends Fragment implements DetailsView {

    private DetailsPresenter presenter;
    private TextView title, categoryArea, ingredientsList, instructions;
    private WebView videoView;
    private ImageView thumbnail;
    private ImageView backBtn, saveBtn, planBtn;
    private Meal currentMeal;

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
        saveBtn = view.findViewById(R.id.saveBtn);
        planBtn = view.findViewById(R.id.planBtn);

        presenter = new DetailsPresenter(this,new Repository(getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            String mealId = bundle.getString("mealId");
            if (mealId != null) {
                presenter.loadMealDetails(mealId);
            }
        }

        backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        saveBtn.setOnClickListener(v -> {
            if(currentMeal != null)
            {
                presenter.saveMeal(currentMeal);
                Toast.makeText(getContext(), "Meal saved", Toast.LENGTH_SHORT).show();
            }
        });
        planBtn.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    public void showMealDetails(Meal meal) {
        currentMeal = meal;
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

    public void reloadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String mealId = bundle.getString("mealId");
            if (mealId != null) {
                presenter.loadMealDetails(mealId);
            }
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    Date selectedDate = calendar.getTime();

                    String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    presenter.scheduleMeal(currentMeal,selectedDate);
                    Toast.makeText(getContext(), "Meal scheduled for " + formattedDate, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "showDatePickerDialog: " + formattedDate);
                },
                year, month, day
        );

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

}