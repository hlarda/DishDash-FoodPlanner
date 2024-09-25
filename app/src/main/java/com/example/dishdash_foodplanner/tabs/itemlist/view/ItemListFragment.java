package com.example.dishdash_foodplanner.tabs.itemlist.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.tabs.itemlist.view.AdapterMeal;
import com.example.dishdash_foodplanner.tabs.itemlist.presenter.ItemListPresenter;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment implements ItemListView, AdapterMeal.MealClickListener {
    private TextView itemlistTitle;
    private RecyclerView recyclerView;
    private AdapterMeal mealAdapter;
    private ItemListPresenter presenter;
    private List<Meal> meals = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        presenter     = new ItemListPresenter(this, new Client());

        itemlistTitle = view.findViewById(R.id.itemlist_title);
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
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClicked(Meal meal) {
        Toast.makeText(getContext(), meal.strMeal, Toast.LENGTH_SHORT).show();
    }
}
