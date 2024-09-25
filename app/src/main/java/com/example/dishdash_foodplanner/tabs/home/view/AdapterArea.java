package com.example.dishdash_foodplanner.tabs.home.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dishdash_foodplanner.R;
import com.example.dishdash_foodplanner.model.POJO.Area;

import java.util.List;

public class AdapterArea extends RecyclerView.Adapter<com.example.dishdash_foodplanner.tabs.home.view.AdapterArea.AreaViewHolder> {

    private final Context context;
    private final List<Area> areas;
    private final AreaClickListener listener;

    public AdapterArea(Context context, List<Area> areas, AreaClickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_area, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.dishdash_foodplanner.tabs.home.view.AdapterArea.AreaViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.areaView.setText(area.strArea);
        holder.itemView.setOnClickListener(v -> listener.onAreaClicked(area));
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public static class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView areaView;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            areaView = itemView.findViewById(R.id.area_view);
        }
    }

    public interface AreaClickListener {
        void onAreaClicked(Area area);
    }
}
