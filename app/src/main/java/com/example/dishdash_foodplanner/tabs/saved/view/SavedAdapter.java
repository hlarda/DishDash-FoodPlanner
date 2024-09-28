package com.example.dishdash_foodplanner.tabs.saved.view;

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

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {

    private final Context context;
    private final List<Meal> meals;
    private final SavedListener listener;

    public SavedAdapter(Context context, List<Meal> meals, SavedListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_saved, parent, false);
        return new SavedViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.title.setText(meal.strMeal);
        holder.categoryArea.setText(meal.strCategory + " | " + meal.strArea);
        Glide.with(context).load(meal.strMealThumb)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);
        holder.removeFromSaved.setOnClickListener(v -> listener.onRemoveFromSavedClicked(meal));
        holder.itemView.setOnClickListener(v -> listener.onMealClicked(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class SavedViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView categoryArea;
        ImageView removeFromSaved;

        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            categoryArea = itemView.findViewById(R.id.category_area);
            removeFromSaved = itemView.findViewById(R.id.deleteBtn);
        }
    }
}

