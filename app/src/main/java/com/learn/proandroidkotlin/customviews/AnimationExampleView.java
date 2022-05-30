package com.learn.proandroidkotlin.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class AnimationExampleView extends View {
    private static final int BACKGROUND_COLOR = Color.GRAY;
    private static final int FOREGROUND_COLOR = Color.GREEN;
    private static final int QUAD_SIZE = 50;

    private float[] angle;
    private Paint paint;
    private Paint paintLine;
    private float angle1 = 0f;
    private long timeStartMillis;
    private long timeStartNanos;
    private long timeStartElapsed;

    public AnimationExampleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public AnimationExampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(FOREGROUND_COLOR);
        paint.setTextSize(48.0f);

        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setAntiAlias(true);
        paintLine.setStrokeWidth(10f);
        paintLine.setColor(Color.RED);

        angle = new float[4];
        for (int i = 0; i < 4; i++) {
            angle[i] = 0.0f;
        }

        ValueAnimator angleAnimator = ValueAnimator.ofFloat(0, 360.f);
        angleAnimator.setDuration(1500);
        angleAnimator.setRepeatMode(ValueAnimator.REVERSE);
        angleAnimator.setRepeatCount(ValueAnimator.INFINITE);

        angleAnimator.addUpdateListener(animation -> {
            angle1 = (float) animation.getAnimatedValue(); // lay ra gia tri hien tai cua animation
            Log.d("TTTT", "Angle = " + angle1);
            invalidate();
        });
        angleAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(BACKGROUND_COLOR); // ve mau nen cua view
        // check time.
        if (timeStartMillis == -1) {
            timeStartMillis = System.currentTimeMillis();
        }

        if (timeStartNanos == -1) {
            timeStartNanos = System.nanoTime();
        }

        if (timeStartElapsed == -1) {
            timeStartElapsed = SystemClock.elapsedRealtime();
        }

        angle[0] += 0.2f;
        angle[1] = (System.currentTimeMillis() - timeStartMillis) * 0.02f;
        angle[2] = (System.nanoTime() - timeStartNanos) * 0.02f * 0.000001f;
        angle[3] = (SystemClock.elapsedRealtime() - timeStartElapsed) * 0.02f;


        // lay ra width, height cua view
        int width = getWidth();
        int height = getHeight();

        canvas.drawLine(width / 2.f, 0, width / 2f, (float) height, paintLine);
        canvas.drawLine(0, height / 2.f, width, height / 2.f, paintLine);


        // draw 4 quads on the screen.
        int wh = width / 2;
        int hh = height / 2;

        int qs = (wh * QUAD_SIZE) / 100;

        // top left
        canvas.save();
        canvas.translate(wh / 2 - qs / 2, hh / 2 - qs / 2);
//        canvas.rotate(angle1, qs / 2, qs / 2);
        canvas.rotate(angle[0], qs / 2, qs / 2);
        canvas.drawRect(0, 0, qs, qs, paint);
        canvas.restore();
    }
}
