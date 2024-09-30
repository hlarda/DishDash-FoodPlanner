package com.example.dishdash_foodplanner.tabs.plan.view;
import static android.content.ContentValues.TAG;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.plan.presenter.PlanPresenter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlanFragment extends Fragment implements PlanView, PlanListener {
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private ImageView backBtn;
    private PlanAdapter adapter;
    private Date selectedDate;
    private PlanPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        backBtn = view.findViewById(R.id.backBtn);
        recyclerView= view.findViewById(R.id.planList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        presenter = new PlanPresenter(this, new Repository(getContext()), getViewLifecycleOwner());
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = Date.from(LocalDate.of(year, month + 1, dayOfMonth).atStartOfDay().toInstant(java.time.ZoneOffset.UTC));
            Log.d(TAG, "Selected date: " + selectedDate.toString());
            presenter.loadPlansForDate(selectedDate);
        });
        return view;
    }

    @Override
    public void showPlannedMeals(List<MealPlan> mealPlans) {
        Log.d(TAG, "Planned meals size: " + mealPlans.size());
        List<Meal> meals = mealPlans.stream().map(plan -> {
            Meal meal = new Meal();
            meal.idMeal = plan.mealId;
            meal.strMeal = plan.mealName;
            meal.strMealThumb = plan.imageUrl;
            meal.strCategory = plan.category;
            meal.strArea = plan.area;
            return meal;
        }).collect(Collectors.toList());
        adapter = new PlanAdapter(getContext(), meals, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
    }

    @Override
    public void onRemoveFromPlanClicked(Meal meal) {
        presenter.removeMealFromDate(new MealPlan(meal, selectedDate));
        Log.d(TAG, "onRemoveFromPlanClicked: " + meal.strMeal);
    }

    @Override
    public void onMealClicked(Meal meal) {
        Log.i(TAG, "onMealClicked: " + meal.strMeal);
    }
}