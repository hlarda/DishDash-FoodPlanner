package com.example.dishdash_foodplanner.network.response;

import java.util.List;

public interface AppNetworkCallback<T> {
    void onSuccess(List<T> items);
    void onFailure(String errorMsg);
}
