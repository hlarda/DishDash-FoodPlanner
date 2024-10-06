package com.example.dishdash_foodplanner.tabs.details.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailsFragment extends Fragment implements DetailsView {

    private DetailsPresenter presenter;
    private TextView title, categoryArea, instructions;
    private WebView videoView;
    private ImageView thumbnail;
    private ImageView backBtn, saveBtn, planBtn;
    private Meal currentMeal;
    private ProgressBar progressBar;
    private TextView ingredients_label, instructions_label, video;
    private RecyclerView ingredientsRecyclerView;
    private MeasureIngredientAdapter ingredientAdapter;
    private List<String> ingredientList = new ArrayList<>();
    private List<String> measureList = new ArrayList<>();
    private CardView mealCard, videoCard;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize all views
        title = view.findViewById(R.id.title);
        categoryArea = view.findViewById(R.id.category_area);
        instructions = view.findViewById(R.id.instructions);
        videoView = view.findViewById(R.id.videoView);
        thumbnail = view.findViewById(R.id.thumbnail);
        backBtn = view.findViewById(R.id.backBtn);
        saveBtn = view.findViewById(R.id.saveBtn);
        planBtn = view.findViewById(R.id.planBtn);
        progressBar = view.findViewById(R.id.progressBar);
        ingredients_label = view.findViewById(R.id.ingredients_label);
        instructions_label = view.findViewById(R.id.instructions_label);
        video = view.findViewById(R.id.video);
        mealCard = view.findViewById(R.id.cardView);
        videoCard = view.findViewById(R.id.videoCard);


        ingredientsRecyclerView = view.findViewById(R.id.ingredients_list);
        ingredientsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        ingredientAdapter = new MeasureIngredientAdapter(getContext(), ingredientList, measureList);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);

        // Initialize presenter
        presenter = new DetailsPresenter(this, new Repository(getContext()));

        // Load meal details based on the passed arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String mealId = bundle.getString("mealId");
            String source = bundle.getString("source");
            if (mealId != null) {
                if ("saved".equals(source)) {
                    presenter.loadSavedMeal(mealId);
                } else if ("plan".equals(source)) {
                    presenter.loadPlannedMeal(mealId);
                } else {
                    presenter.loadMealDetails(mealId);
                }
            }
        }

        // Handle back button click
        backBtn.setOnClickListener(v -> getActivity().onBackPressed());

        // Handle save button click
        saveBtn.setOnClickListener(v -> {
            if (currentMeal != null) {
                presenter.saveMeal(currentMeal);
                Toast.makeText(getContext(), "Meal saved", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle plan button click
        planBtn.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    @Override
    public void showMealDetails(Meal meal) {
        currentMeal = meal;

        // Hide the progress bar and show relevant UI components
        progressBar.setVisibility(View.GONE);
        thumbnail.setVisibility(View.VISIBLE);
        categoryArea.setVisibility(View.VISIBLE);
        instructions.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.VISIBLE);
        planBtn.setVisibility(View.VISIBLE);
        ingredients_label.setVisibility(View.VISIBLE);
        instructions_label.setVisibility(View.VISIBLE);
        video.setVisibility(View.VISIBLE);
        ingredientsRecyclerView.setVisibility(View.VISIBLE);
        mealCard.setVisibility(View.VISIBLE);
        videoCard.setVisibility(View.VISIBLE);

        // Load meal thumbnail
        Glide.with(getContext())
                .load(meal.strMealThumb)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnail);

        // Set meal details in TextViews
        title.setText(meal.strMeal);
        categoryArea.setText(meal.strCategory + " | " + meal.strArea);

        // Clear previous data and populate the new ingredient and measure lists
        ingredientList.clear();
        measureList.clear();
        addIngredient(meal.strIngredient1, meal.strMeasure1);
        addIngredient(meal.strIngredient2, meal.strMeasure2);
        addIngredient(meal.strIngredient3, meal.strMeasure3);
        addIngredient(meal.strIngredient4, meal.strMeasure4);
        addIngredient(meal.strIngredient5, meal.strMeasure5);
        addIngredient(meal.strIngredient6, meal.strMeasure6);
        addIngredient(meal.strIngredient7, meal.strMeasure7);
        addIngredient(meal.strIngredient8, meal.strMeasure8);
        addIngredient(meal.strIngredient9, meal.strMeasure9);
        addIngredient(meal.strIngredient10, meal.strMeasure10);

        // Notify the adapter of data changes
        ingredientAdapter.notifyDataSetChanged();

        // Set instructions
        instructions.setText(meal.strInstructions);

        // Configure video view
        videoView.getSettings().setJavaScriptEnabled(true);
        String videoUrl = meal.strYoutube.replace("watch?v=", "embed/");
        videoView.loadUrl(videoUrl);
    }

    private void addIngredient(String ingredient, String measure) {
        if (ingredient != null && !ingredient.isEmpty()) {
            ingredientList.add(ingredient);
            measureList.add(measure != null ? measure : "");
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

    // Show DatePicker dialog for scheduling a meal
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
            presenter.scheduleMeal(currentMeal, selectedDate);
            Toast.makeText(getContext(), "Meal scheduled for " + formattedDate, Toast.LENGTH_SHORT).show();

            //add to local calendar
            long startTime = calendar.getTimeInMillis();
            long endTime = startTime + 60 * 60 * 1000; // 1 hour duration
            presenter.addMealToCalendar(getContext(), currentMeal.strMeal, "Meal: " + currentMeal.strMeal, startTime, endTime);
        },
                year, month, day
        );

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}
