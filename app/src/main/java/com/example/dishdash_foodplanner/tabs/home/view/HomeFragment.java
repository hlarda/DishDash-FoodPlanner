package com.example.dishdash_foodplanner.tabs.home.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Area;
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsFragment;
import com.example.dishdash_foodplanner.tabs.home.presenter.HomePresenter;

import com.example.dishdash_foodplanner.tabs.home.view.AdapterCategoryIngredient.CategoryClickListener;
import com.example.dishdash_foodplanner.tabs.home.view.AdapterCategoryIngredient.ItemClickListener;
import com.example.dishdash_foodplanner.tabs.home.view.AdapterArea.AreaClickListener;
import com.example.dishdash_foodplanner.tabs.home.view.AdapterMeal.MealClickListener;
import com.example.dishdash_foodplanner.tabs.itemlist.view.ItemListFragment;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView, CategoryClickListener, AreaClickListener, MealClickListener, ItemClickListener<Category>{

    private HomePresenter presenter;
    private AdapterArea adapterArea;
    private AdapterCategoryIngredient<Category> adapterCategory;
    private AdapterCategoryIngredient<Ingredient> adapterIngredient;
    private AdapterMeal adapterMeal;
    private List<Area> areas = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Meal> randomList = new ArrayList<>();
    private ItemListFragment itemListFragment;
    private ProgressBar progressBarArea, progressBarCategory, progressBarMeal, progressBarIngredient;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this, new Repository(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView areaRecyclerView = view.findViewById(R.id.areaList);
        adapterArea = new AdapterArea(getContext(), areas, this);
        areaRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        areaRecyclerView.setAdapter(adapterArea);

        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryList);
        adapterCategory = new AdapterCategoryIngredient<>(getContext(), categories, this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(adapterCategory);

        RecyclerView ingredientRecyclerView = view.findViewById(R.id.ingredientList);
        adapterIngredient = new AdapterCategoryIngredient<>(getContext(), ingredients, new ItemClickListener<Ingredient>() {
            @Override
            public void onItemClicked(Ingredient item) {
                Bundle bundle = new Bundle();
                bundle.putString("selectedIngredient", item.strIngredient);
                itemListFragment = new ItemListFragment();
                itemListFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, itemListFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        ingredientRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        ingredientRecyclerView.setAdapter(adapterIngredient);

        RecyclerView mealRecyclerView = view.findViewById(R.id.pickedList);
        adapterMeal = new AdapterMeal(getContext(), randomList, this);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mealRecyclerView.setAdapter(adapterMeal);

        progressBarArea = view.findViewById(R.id.progressBarArea);
        progressBarCategory = view.findViewById(R.id.progressBarCategory);
        progressBarMeal = view.findViewById(R.id.progressBarMeal);
        progressBarIngredient = view.findViewById(R.id.progressBarIngredient);

        showLoading();
        presenter.loadAreas();
        presenter.loadCategories();
        presenter.loadIngredients();
        presenter.loadRandomMeals();

        return view;
    }

    private void showLoading() {
        progressBarArea.setVisibility(View.VISIBLE);
        progressBarCategory.setVisibility(View.VISIBLE);
        progressBarMeal.setVisibility(View.VISIBLE);
        progressBarIngredient.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBarArea.setVisibility(View.GONE);
        progressBarCategory.setVisibility(View.GONE);
        progressBarMeal.setVisibility(View.GONE);
        progressBarIngredient.setVisibility(View.GONE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showAreas(List<Area> areas) {
        this.areas.clear();
        this.areas.addAll(areas);
        adapterArea.notifyDataSetChanged();
        progressBarArea.setVisibility(View.GONE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        adapterCategory.notifyDataSetChanged();
        progressBarCategory.setVisibility(View.GONE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
        adapterIngredient.notifyDataSetChanged();
        progressBarIngredient.setVisibility(View.GONE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showRandomList(List<Meal> randomList) {
        this.randomList.clear();
        this.randomList.addAll(randomList);
        adapterMeal.notifyDataSetChanged();
        progressBarMeal.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Log.i(TAG, "showError: " + error);
        hideLoading();
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
                .commit();
    }

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

    @Override
    public void onItemClicked(Category item) {
        onCategoryClicked(item);
    }

    public void reloadData() {
        presenter.loadAreas();
        presenter.loadCategories();
        presenter.loadIngredients();
        presenter.loadRandomMeals();
    }
}