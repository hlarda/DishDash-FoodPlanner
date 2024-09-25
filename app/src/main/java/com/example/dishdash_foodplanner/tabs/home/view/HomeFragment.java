package com.example.dishdash_foodplanner.tabs.home.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.network.APIs.Client;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsFragment;
import com.example.dishdash_foodplanner.tabs.home.presenter.HomePresenter;

import com.example.dishdash_foodplanner.tabs.home.view.AdapterCategory.CategoryClickListener;
import com.example.dishdash_foodplanner.tabs.home.view.AdapterArea.AreaClickListener;
import com.example.dishdash_foodplanner.tabs.home.view.AdapterMeal.MealClickListener;
import com.example.dishdash_foodplanner.tabs.itemlist.view.ItemListFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, CategoryClickListener, AreaClickListener, MealClickListener {

    private HomePresenter   presenter;
    private AdapterArea     adapterArea;
    private AdapterCategory adapterCategory;
    private AdapterMeal     adapterMeal;
    private List<Area>      areas = new ArrayList<>();
    private List<Category>  categories = new ArrayList<>();
    private List<Meal>      randomList = new ArrayList<>();
    private ItemListFragment itemListFragment;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this, new Client());
        presenter.loadAreas();
        presenter.loadCategories();
        presenter.loadRandomMeals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView areaRecyclerView = view.findViewById(R.id.areaList);
        adapterArea = new AdapterArea(getContext(), areas, this);
        areaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        areaRecyclerView.setAdapter(adapterArea);

        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryList);
        adapterCategory = new AdapterCategory(getContext(), categories, this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(adapterCategory);

        RecyclerView mealRecyclerView = view.findViewById(R.id.pickedList);
        adapterMeal = new AdapterMeal(getContext(), randomList, this);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mealRecyclerView.setAdapter(adapterMeal);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showAreas(List<Area> areas) {
        this.areas.clear();
        this.areas.addAll(areas);
        adapterArea.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        adapterCategory.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showRandomList(List<Meal> randomList) {
        this.randomList.clear();
        this.randomList.addAll(randomList);
        adapterMeal.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAreaClicked(Area area) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedArea", area.strArea);
        itemListFragment = new ItemListFragment();
        itemListFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, itemListFragment)
                .addToBackStack(null)
                .commit();    }

    @Override
    public void onCategoryClicked(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedCategory", category.strCategory);
        itemListFragment = new ItemListFragment();
        itemListFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, itemListFragment)
                .addToBackStack(null)
                .commit();
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
}