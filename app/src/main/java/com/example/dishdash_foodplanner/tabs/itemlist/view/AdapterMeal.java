package com.example.dishdash_foodplanner.tabs.itemlist.view;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.List;

public class AdapterMeal extends RecyclerView.Adapter<AdapterMeal.MealViewHolder> {

    private final Context context;
    private final List<Meal> meals;
    private final MealClickListener listener;

    public AdapterMeal(Context context, List<Meal> meals, MealClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new MealViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.title.setText(meal.strMeal);
        Glide.with(context).load(meal.strMealThumb)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(v -> listener.onMealClicked(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface MealClickListener {
        void onMealClicked(Meal meal);
    }
}
