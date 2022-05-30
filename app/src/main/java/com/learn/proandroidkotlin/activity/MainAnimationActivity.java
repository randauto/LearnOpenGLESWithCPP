package com.learn.proandroidkotlin.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

import com.bip.learnopengleswithcpp.R;

public class MainAnimationActivity extends AppCompatActivity {
    private float angle = 0f;
    private View viewCenter;
    ObjectAnimator angleAnimator;
    FlingAnimation flingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_animation);

        initView();

        initGroupView();
    }

    private void initView() {
        viewCenter = findViewById(R.id.viewCenter);
        viewCenter.setOnClickListener(view -> {
            flingAnimation(view);
        });


    }

    private void initGroupView() {
        ViewGroup llContainer = findViewById(R.id.llContainer);

        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.row_item_layout, null);

            View ivDelete = view.findViewById(R.id.viewDelete);
            TextView tvStatus = view.findViewById(R.id.tvStatus);
            tvStatus.setText("Item " + i);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llContainer.removeView((View)v.getParent());
                    llContainer.requestLayout();
                    Log.d("TTTT", "Total View Child: " + llContainer.getChildCount());
                }
            });


            llContainer.addView(view);
        }
    }

    private void flingAnimation(View view) {
        float velocityX = 300f;
        float maxScroll = 1500f;
        if (flingAnimation == null) {
            flingAnimation = new FlingAnimation(view, DynamicAnimation.SCROLL_X);
            flingAnimation.setStartVelocity(-velocityX)
                    .setMinValue(0)
                    .setMaxValue(maxScroll)
                    .setFriction(1.1f);

        }

        flingAnimation.start();
    }

    private void objectAnimationStart(View view) {
        if (angleAnimator == null) {
            angleAnimator = ObjectAnimator.ofFloat(view, View.X, 0, 500);
            angleAnimator.setDuration(1500);
            angleAnimator.setRepeatMode(ValueAnimator.REVERSE);
            angleAnimator.setRepeatCount(ValueAnimator.INFINITE);

            angleAnimator.addUpdateListener(animation -> {
                angle = (float) animation.getAnimatedValue(); // lay ra gia tri hien tai cua animation
                Log.d("TTTT", "Angle = " + angle);

            });
        }

        angleAnimator.start();
    }
}