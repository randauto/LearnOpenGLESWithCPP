package com.learn.proandroidkotlin.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bip.learnopengleswithcpp.R;
import com.learn.proandroidkotlin.utils.ImageHelper;

public class DisplayMyViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ImageView img_meizi;
    private SeekBar sb_hue;
    private SeekBar sb_saturation;
    private SeekBar sb_lum;
    private final static int MAX_VALUE = 255;
    private final static int MID_VALUE = 127;
    private float mHue = 0.0f;
    private float mStauration = 1.0f;
    private float mLum = 1.0f;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_view);

/*        Chart chart = (Chart) findViewById(R.id.myChart);

        float[] data = new float[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = (float) Math.random() * 10.f;
        }
        chart.setDataPoints(data);*/

        initHueColorMatrix();
    }

    private void initHueColorMatrix() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl1);

        img_meizi = findViewById(R.id.img_meizi);
        sb_hue = findViewById(R.id.sb_hue);
        sb_saturation = findViewById(R.id.sb_saturation);
        sb_lum = findViewById(R.id.sb_lum);

        img_meizi.setImageBitmap(mBitmap);

        sb_hue.setMax(MAX_VALUE);
        sb_hue.setProgress(MID_VALUE);
        sb_saturation.setMax(MAX_VALUE);
        sb_saturation.setProgress(MID_VALUE);
        sb_lum.setMax(MAX_VALUE);
        sb_lum.setProgress(MID_VALUE);

        sb_hue.setOnSeekBarChangeListener(this);
        sb_saturation.setOnSeekBarChangeListener(this);
        sb_lum.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
                break;
            case R.id.sb_saturation:
                mStauration = progress * 1.0F / MID_VALUE;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0F / MID_VALUE;
                break;
        }

        img_meizi.setImageBitmap(ImageHelper.handleImageEffect(mBitmap, mHue, mStauration, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}