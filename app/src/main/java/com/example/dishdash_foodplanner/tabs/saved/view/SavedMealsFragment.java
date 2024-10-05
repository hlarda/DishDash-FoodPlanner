package com.example.dishdash_foodplanner.tabs.saved.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsFragment;
import com.example.dishdash_foodplanner.tabs.home.view.HomeFragment;
import com.example.dishdash_foodplanner.tabs.saved.presenter.SavedPresenter;

import java.util.List;

public class SavedMealsFragment extends Fragment implements SavedView, SavedListener{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SavedAdapter adapter;
    SavedPresenter presenter;
    ImageView backBtn, nothing;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = view.findViewById(R.id.saved_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        presenter = new SavedPresenter(this, new Repository(getContext()));
        presenter.loadSavedMeals();

        nothing = view.findViewById(R.id.nothingSaved);


        return view;
    }

    @Override
    public void onRemoveFromSavedClicked(Meal meal) {
        new AlertDialog.Builder(getContext())
                .setTitle("Remove Meal")
                .setMessage("Are you sure you want to remove this meal from saved?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    presenter.removeFromSaved(meal);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public void onMealClicked(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.idMeal);
        bundle.putString("source", "saved");
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showSavedMeals(List<Meal> meals) {
        if (meals.size() == 0) {
            nothing.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            nothing.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new SavedAdapter(getContext(), meals, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showError(String error) {
        Log.e(TAG, "showError: " + error);
    }
}
