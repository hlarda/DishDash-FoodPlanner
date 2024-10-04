package com.example.dishdash_foodplanner.tabs.details.view;

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
import java.util.List;

public class MeasureIngredientAdapter extends RecyclerView.Adapter<MeasureIngredientAdapter.IngredientViewHolder> {

    private final Context context;
    private final List<String> ingredients;
    private final List<String> measures;

    public MeasureIngredientAdapter(Context context, List<String> ingredients, List<String> measures) {
        this.context = context;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_measure_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String ingredient = ingredients.get(position);
        String measure = measures.get(position);
        holder.title.setText(ingredient);
        holder.measure.setText(measure);
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + ingredient + ".png")
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView measure;
        ImageView thumbnail;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            measure = itemView.findViewById(R.id.measureTxt);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}