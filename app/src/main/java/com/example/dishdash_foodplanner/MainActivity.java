package com.example.dishdash_foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimation);
        lottieAnimationView.setImageAssetsFolder("images/");

        getStartedBtn = findViewById(R.id.startButton);
        getStartedBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
        });
    }
}