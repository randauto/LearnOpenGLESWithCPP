package com.learn.proandroidkotlin.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyViewTouch extends View {
    public float X = 50;

    public float Y = 50;

    Paint paint = new Paint();

    public MyViewTouch(Context context) {
        super(context);
        init(context, null);
    }

    public MyViewTouch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(X, Y, 50, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.X = event.getX();
        this.Y = event.getY();

        invalidate();
        return true;
    }
}
