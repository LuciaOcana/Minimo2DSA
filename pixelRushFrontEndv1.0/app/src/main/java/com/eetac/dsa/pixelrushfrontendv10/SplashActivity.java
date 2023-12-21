package com.eetac.dsa.pixelrushfrontendv10;

// SplashActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//To close the account automatically
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 1000; // Splash screen timeout in milliseconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Use a Handler to delay the transition to the main activity
        new Handler().postDelayed(() -> {
            // Start the main activity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            // Close the splash activity
            finish();
        }, SPLASH_TIMEOUT);
    }

}
