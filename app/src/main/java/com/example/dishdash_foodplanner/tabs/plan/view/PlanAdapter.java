package com.example.dishdash_foodplanner.tabs.plan.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.MealPlan;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.SavedViewHolder> {

    private final Context context;
    private List<MealPlan> mealPlans;
    private final PlanListener listener;

    public PlanAdapter(Context context, List<MealPlan> mealPlans, PlanListener listener) {
        this.context = context;
        this.mealPlans = mealPlans;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_plan, parent, false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);
        holder.title.setText(mealPlan.strMeal);
        holder.categoryArea.setText(mealPlan.strCategory + " | " + mealPlan.strArea);
        holder.date.setText(mealPlan.date.toString());
        Glide.with(context).load(mealPlan.strMealThumb)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);
        holder.removeFromPlanned.setOnClickListener(v -> {
            Log.d(TAG, "Remove button clicked for mealPlan: " + mealPlan.strMeal);
            listener.onRemoveFromPlanClicked(mealPlan);
        });
        holder.itemView.setOnClickListener(v -> listener.onMealClicked(mealPlan));
    }

    @Override
    public int getItemCount() {
        return mealPlans != null ? mealPlans.size() : 0;
    }

    public static class SavedViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView categoryArea;
        TextView date;
        ImageView removeFromPlanned;

        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            categoryArea = itemView.findViewById(R.id.category_area);
            removeFromPlanned = itemView.findViewById(R.id.deleteBtn);
            date = itemView.findViewById(R.id.date);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMealPlans(List<MealPlan> newMealPlans) {
        this.mealPlans = newMealPlans;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearMealPlans() {
        this.mealPlans.clear();
        notifyDataSetChanged();
    }

}
