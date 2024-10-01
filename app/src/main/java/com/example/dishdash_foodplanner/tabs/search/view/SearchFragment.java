package com.example.dishdash_foodplanner.tabs.search.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.model.db.Repository;
import com.example.dishdash_foodplanner.network.response.AppNetworkCallback;
import com.example.dishdash_foodplanner.tabs.search.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView    searchView;
    private RecyclerView  searchResultsRecyclerView;
    private SearchAdapter searchAdapter;
    private Repository    repository;
    private ImageView     noResultsImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchView);
        searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        noResultsImage = view.findViewById(R.id.noResultsImage);

        searchAdapter = new SearchAdapter(new ArrayList<>());
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResultsRecyclerView.setAdapter(searchAdapter);

        repository = new Repository(getContext());

        searchView.setQueryHint("Search for meals...");
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    searchAdapter.updateMeals(new ArrayList<>());
                    noResultsImage.setVisibility(View.GONE);
                    searchResultsRecyclerView.setVisibility(View.GONE);
                } else {
                    performSearch(newText);
                }
                return true;
            }
        });

        return view;
    }

    private void performSearch(String query) {
        repository.searchMealsByName(query, new AppNetworkCallback<Meal>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (meals != null && !meals.isEmpty()) {
                    searchAdapter.updateMeals(meals);
                    noResultsImage.setVisibility(View.GONE);
                    searchResultsRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    searchAdapter.updateMeals(new ArrayList<>()); // Handle empty list
                    noResultsImage.setVisibility(View.VISIBLE);
                    searchResultsRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error
                searchAdapter.updateMeals(new ArrayList<>()); // Clear the list on failure
                noResultsImage.setVisibility(View.VISIBLE);
                searchResultsRecyclerView.setVisibility(View.GONE);
            }
        });
    }
}