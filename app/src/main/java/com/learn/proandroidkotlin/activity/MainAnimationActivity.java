package com.learn.proandroidkotlin.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bip.learnopengleswithcpp.R;

public class MainAnimationActivity extends AppCompatActivity {
    private float angle = 0f;
    private View viewCenter;
    ValueAnimator angleAnimator;

    private float scaleX = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_animation);

        initView();

        angleAnimator = ValueAnimator.ofFloat(0, 360.f);
        angleAnimator.setDuration(1500);
        angleAnimator.setRepeatMode(ValueAnimator.REVERSE);
        angleAnimator.setRepeatCount(ValueAnimator.INFINITE);

        angleAnimator.addUpdateListener(animation -> {
            angle = (float) animation.getAnimatedValue(); // lay ra gia tri hien tai cua animation
            Log.d("TTTT", "Angle = " + angle);
            Log.d("TTTT", "scaleX = " + scaleX);
            viewCenter.setRotation(angle);
        });
    }

    private void initView() {
        viewCenter = findViewById(R.id.viewCenter);
        viewCenter.setOnClickListener(view -> {

            angleAnimator.start();
        });
    }
}