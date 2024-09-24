package com.example.dishdash_foodplanner.network.APIs;

import java.util.List;

public interface NetworkCallback<T> {
    void onSuccess(List<T> items);
    void onFailure(String errorMsg);
}
