package com.example.dishdash_foodplanner.tabs.itemlist.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsFragment;
import com.example.dishdash_foodplanner.tabs.itemlist.presenter.ItemListPresenter;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment implements ItemListView, AdapterMeal.MealClickListener {
    private TextView itemlistTitle;
    private RecyclerView recyclerView;
    private AdapterMeal mealAdapter;
    ImageView backBtn;
    private ItemListPresenter presenter;
    private List<Meal> meals = new ArrayList<>();

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        presenter     = new ItemListPresenter(this, new Repository(getContext()));

        itemlistTitle = view.findViewById(R.id.title);
        recyclerView  = view.findViewById(R.id.itemList);
        mealAdapter   = new AdapterMeal(getContext(), meals,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mealAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String selectedCategory = bundle.getString("selectedCategory");
            String selectedArea = bundle.getString("selectedArea");

            if (selectedCategory != null) {
                itemlistTitle.setText("Top " + selectedCategory+ " Dishes" );
                presenter.loadMealsByCategory(selectedCategory);
            } else if (selectedArea != null) {
                itemlistTitle.setText("Top " + selectedArea + " Cuisine");
                presenter.loadMealsByArea(selectedArea);
            }
        }
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMealList(List<Meal> randomList) {
        meals.clear();
        meals.addAll(randomList);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
    }

    @Override
    public void onMealClicked(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.idMeal);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void reloadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String selectedCategory = bundle.getString("selectedCategory");
            String selectedArea = bundle.getString("selectedArea");

            if (selectedCategory != null) {
                presenter.loadMealsByCategory(selectedCategory);
            } else if (selectedArea != null) {
                presenter.loadMealsByArea(selectedArea);
            }
        }
    }

}
