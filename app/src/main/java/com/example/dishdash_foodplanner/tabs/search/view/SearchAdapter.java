package com.example.dishdash_foodplanner.tabs.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.example.dishdash_foodplanner.tabs.home.view.AdapterMeal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Meal> meals;
    private final MealClickListener listener;

    public SearchAdapter(List<Meal> meals, MealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealTitle.setText(meal.strMeal);
        Glide.with(holder.itemView.getContext())
                .load(meal.strMealThumb)
                .into(holder.mealImage);
        holder.itemView.setOnClickListener(v -> listener.onMealClicked(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateMeals(List<Meal> newMeals) {
        meals.clear();
        meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealTitle;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.thumbnail);
            mealTitle = itemView.findViewById(R.id.title);
        }
    }

    public interface MealClickListener {
        void onMealClicked(Meal meal);
    }
}