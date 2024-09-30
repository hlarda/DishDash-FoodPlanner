package com.example.dishdash_foodplanner.model.db;
import androidx.room.TypeConverter;

import com.example.dishdash_foodplanner.model.POJO.Meal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromMeal(Meal meal) {
        if (meal == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Meal>() {}.getType();
        return gson.toJson(meal, type);
    }

    @TypeConverter
    public static Meal toMeal(String mealString) {
        if (mealString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Meal>() {}.getType();
        return gson.fromJson(mealString, type);
    }
}