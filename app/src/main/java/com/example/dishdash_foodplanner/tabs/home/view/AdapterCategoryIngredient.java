package com.example.dishdash_foodplanner.tabs.home.view;

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
import com.example.dishdash_foodplanner.model.POJO.Category;
import com.example.dishdash_foodplanner.model.POJO.Ingredient;
import java.util.List;

public class AdapterCategoryIngredient<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<T> items;
    private final ItemClickListener<T> listener;

    public enum ViewType {
        CATEGORY,
        INGREDIENT
    }

    public AdapterCategoryIngredient(Context context, List<T> items, ItemClickListener<T> listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        T item = items.get(position);
        if (item instanceof Category) {
            return ViewType.CATEGORY.ordinal();
        } else if (item instanceof Ingredient) {
            return ViewType.INGREDIENT.ordinal();
        }
        throw new IllegalArgumentException("Invalid item type");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ViewType.CATEGORY.ordinal()) {
            view = LayoutInflater.from(context).inflate(R.layout.card_category, parent, false);
            return new CategoryViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.card_ingredient, parent, false);
            return new IngredientViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T item = items.get(position);
        if (holder instanceof CategoryViewHolder) {
            Category category = (Category) item;
            ((CategoryViewHolder) holder).title.setText(category.strCategory);
            Glide.with(context).load(category.strCategoryThumb)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(((CategoryViewHolder) holder).thumbnail);
        } else if (holder instanceof IngredientViewHolder) {
            Ingredient ingredient = (Ingredient) item;
            ((IngredientViewHolder) holder).title.setText(ingredient.strIngredient);
            Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + ingredient.strIngredient + "-Small.png")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(((IngredientViewHolder) holder).thumbnail);
        }
        holder.itemView.setOnClickListener(v -> listener.onItemClicked(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
        }
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface ItemClickListener<T> {
        void onItemClicked(T item);
    }

    public interface CategoryClickListener {
        void onCategoryClicked(Category category);
    }
}