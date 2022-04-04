package com.learn.proandroidkotlin.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bip.learnopengleswithcpp.R;
import com.learn.proandroidkotlin.customviews.Chart;

public class DisplayMyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_view);

        Chart chart = (Chart) findViewById(R.id.myChart);

        float[] data = new float[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = (float) Math.random() * 10.f;
        }
        chart.setDataPoints(data);
    }
}