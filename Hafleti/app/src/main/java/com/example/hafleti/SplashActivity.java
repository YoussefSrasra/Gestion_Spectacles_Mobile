package com.example.hafleti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 4000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView logoText = findViewById(R.id.hafletiText);

        // Scale animation (pulse effect)
        ScaleAnimation scaleAnim = new ScaleAnimation(
                1f, 1.2f, 1f, 1.2f,
                AnimationSet.RELATIVE_TO_SELF, 0.5f,
                AnimationSet.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnim.setDuration(2000);
        scaleAnim.setRepeatCount(1);
        scaleAnim.setRepeatMode(ScaleAnimation.REVERSE);

        // Alpha animation (brightness effect)
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.5f);
        alphaAnim.setDuration(2000);
        alphaAnim.setRepeatCount(1);
        alphaAnim.setRepeatMode(AlphaAnimation.REVERSE);

        // Combine animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);
        logoText.startAnimation(animationSet);

        // After 4 seconds, go to HomeActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, com.example.hafleti.Home.HomeActivity.class));
            finish();
        }, SPLASH_DURATION);
    }
}
