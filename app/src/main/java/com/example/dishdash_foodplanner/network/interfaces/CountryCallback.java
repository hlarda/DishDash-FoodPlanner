package com.example.dishdash_foodplanner.network.interfaces;

import com.example.dishdash_foodplanner.model.POJO.Country;

import java.util.List;

public interface CountryCallback {
    public void onSuccessCountries(List<Country> countries);
    public void onFailureCountries(String errorMsg);
}
