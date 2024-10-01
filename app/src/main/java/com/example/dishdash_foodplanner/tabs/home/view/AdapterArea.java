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
import com.example.dishdash_foodplanner.model.POJO.Area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterArea extends RecyclerView.Adapter<AdapterArea.AreaViewHolder> {

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
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.areaView.setText(area.strArea);

        String isoCode = CountryAdjectiveMapper.getIsoCode(area.strArea);
        if (isoCode != null && !isoCode.equals("unknown")) {
            String flagUrl = "https://www.themealdb.com/images/icons/flags/big/64/" + isoCode + ".png";
            Glide.with(context)
                    .load(flagUrl)
                    .into(holder.flagImage);
        }

        holder.itemView.setOnClickListener(v -> listener.onAreaClicked(area));
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public static class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView areaView;
        ImageView flagImage;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            areaView = itemView.findViewById(R.id.title);
            flagImage = itemView.findViewById(R.id.thumbnail);
        }
    }

    public interface AreaClickListener {
        void onAreaClicked(Area area);
    }
}
class CountryAdjectiveMapper {
    private static final Map<String, String> adjectiveToIsoMap = new HashMap<>();

    static {
        adjectiveToIsoMap.put("American", "us");
        adjectiveToIsoMap.put("British", "gb");
        adjectiveToIsoMap.put("Canadian", "ca");
        adjectiveToIsoMap.put("Chinese", "cn");
        adjectiveToIsoMap.put("Croatian", "hr");
        adjectiveToIsoMap.put("Dutch", "nl");
        adjectiveToIsoMap.put("Egyptian", "eg");
        adjectiveToIsoMap.put("Filipino", "ph");
        adjectiveToIsoMap.put("French", "fr");
        adjectiveToIsoMap.put("Greek", "gr");
        adjectiveToIsoMap.put("Indian", "in");
        adjectiveToIsoMap.put("Irish", "ie");
        adjectiveToIsoMap.put("Italian", "it");
        adjectiveToIsoMap.put("Jamaican", "jm");
        adjectiveToIsoMap.put("Japanese", "jp");
        adjectiveToIsoMap.put("Kenyan", "ke");
        adjectiveToIsoMap.put("Malaysian", "my");
        adjectiveToIsoMap.put("Mexican", "mx");
        adjectiveToIsoMap.put("Moroccan", "ma");
        adjectiveToIsoMap.put("Polish", "pl");
        adjectiveToIsoMap.put("Portuguese", "pt");
        adjectiveToIsoMap.put("Russian", "ru");
        adjectiveToIsoMap.put("Spanish", "es");
        adjectiveToIsoMap.put("Thai", "th");
        adjectiveToIsoMap.put("Tunisian", "tn");
        adjectiveToIsoMap.put("Turkish", "tr");
        adjectiveToIsoMap.put("Ukrainian", "ua");
        adjectiveToIsoMap.put("Vietnamese", "vn");
    }

    public static String getIsoCode(String adjective) {
        return adjectiveToIsoMap.get(adjective);
    }
}