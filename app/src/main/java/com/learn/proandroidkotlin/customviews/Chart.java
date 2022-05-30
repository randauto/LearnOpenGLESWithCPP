package com.learn.proandroidkotlin.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Chart extends View {
    private Paint linePaint;

    private float[] dataPoints;
    private float minValue;
    private float maxValue;
    private float verticalDelta;


    public Chart(Context context) {
        super(context);
        init(context, null);
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(8.f);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    public void setDataPoints(float[] originalData) {
        dataPoints = new float[originalData.length];
        minValue = Float.MAX_VALUE;
        maxValue = Float.MIN_VALUE;

        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = originalData[i];
            if (dataPoints[i] < minValue) {
                minValue = dataPoints[i];
            }

            if (dataPoints[i] > maxValue) {
                maxValue = dataPoints[i];
            }
        }

        verticalDelta = maxValue - minValue;

        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = (dataPoints[i] - minValue) / verticalDelta;
        }

        postInvalidate();
    }

/*    public void setDataPoints(float[] originalData) {
        dataPoints = new float[originalData.length];
        minValue = Float.MIN_VALUE;
        maxValue = Float.MAX_VALUE;

        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = originalData[i];
            if (dataPoints[i] < minValue) {
                minValue = dataPoints[i];
            }

            if (dataPoints[i] > maxValue) {
                maxValue = dataPoints[i];
            }
        }

        verticalDelta = maxValue - minValue;
        postInvalidate();
    }*/

    private Path graphPath;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(255, 0, 0, 0);

        float leftPadding = getPaddingLeft();
        float topPadding = getPaddingTop();

        float width = canvas.getWidth() - leftPadding - getPaddingRight();
        float height = canvas.getHeight() - topPadding - getPaddingBottom();

        if (graphPath == null) {
            graphPath = new Path();
            graphPath.moveTo(leftPadding, height * dataPoints[0] + topPadding);

            for (int i = 0; i < dataPoints.length; i++) {
                float y = height * dataPoints[i] + topPadding;
                float x = width * (((float) i + 1) / dataPoints.length) + leftPadding;

                graphPath.lineTo(x, y);
            }
        }

        canvas.drawPath(graphPath, linePaint);

    }
}
