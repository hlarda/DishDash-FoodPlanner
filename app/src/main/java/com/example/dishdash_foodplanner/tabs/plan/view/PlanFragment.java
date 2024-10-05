package com.example.dishdash_foodplanner.tabs.plan.view;

import static android.content.ContentValues.TAG;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsFragment;
import com.example.dishdash_foodplanner.tabs.plan.presenter.PlanPresenter;

import android.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlanFragment extends Fragment implements PlanView, PlanListener {
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private ImageView backBtn, nothing;
    private PlanAdapter adapter;
    private Date selectedDate;
    private PlanPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        backBtn = view.findViewById(R.id.backBtn);
        nothing = view.findViewById(R.id.nothingImg);
        recyclerView = view.findViewById(R.id.planList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view1, year, month, day) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            selectedDate = calendar.getTime();
            presenter.loadPlansForDate(selectedDate);
        });

        backBtn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        presenter = new PlanPresenter(this, new Repository(getContext()), getViewLifecycleOwner());

        // Get the current date and load the plans for it
        selectedDate = new Date();
        presenter.loadPlansForDate(selectedDate);

        return view;
    }

    @Override
    public void showPlannedMeals(List<MealPlan> mealPlans) {
        Log.d(TAG, "Planned meals size: " + mealPlans.size());

        if (adapter == null) {
            adapter = new PlanAdapter(getContext(), new ArrayList<>(), this);
            recyclerView.setAdapter(adapter);
        }

        adapter.clearMealPlans();

        if (mealPlans != null && !mealPlans.isEmpty()) {
            adapter.updateMealPlans(mealPlans);
            nothing.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            adapter.updateMealPlans(new ArrayList<>());
            nothing.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Log.d(TAG, "No meals planned for the selected date.");
        }
    }


    @Override
    public void onRemoveFromPlanClicked(MealPlan mealPlan) {
        new AlertDialog.Builder(getContext())
                .setTitle("Remove Meal")
                .setMessage("Are you sure you want to remove this plan?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    presenter.removeMealFromDate(mealPlan);
                    presenter.loadPlansForDate(selectedDate);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
    }

    @Override
    public void onMealClicked(MealPlan mealPlan) {
        Log.i(TAG, "onMealClicked: " + mealPlan.strMeal);
        Bundle bundle = new Bundle();
        bundle.putString("mealId", mealPlan.idMeal);
        bundle.putString("source", "plan");
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}