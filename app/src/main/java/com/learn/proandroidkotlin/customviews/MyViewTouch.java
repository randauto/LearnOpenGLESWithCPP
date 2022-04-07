package com.learn.proandroidkotlin.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyViewTouch extends View {
    public static final int MODE_CLEAR = 1;
    public static final int MODE_NORMAL = 2;
    public float X = 50;

    public float Y = 50;

    private static final String TAG = "MyViewTouch";

    Paint paint = new Paint();


    private Matrix matrix;
    private Canvas canvasBitmap;
    private Bitmap bitmap;

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
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvasBitmap = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: at position [X,Y]: " + "[" + this.X + ", " + this.Y + "]");
        matrix.reset();
        canvasBitmap.save();
        matrix.postTranslate(100f, 100f);

        if (mCurrentMode == MODE_CLEAR) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        }

        canvasBitmap.drawCircle(X, Y, 50, paint);
        canvasBitmap.restore();
        canvas.drawBitmap(bitmap, 0f, 0f, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.X = event.getX();
        this.Y = event.getY();

        Log.d(TAG, "onTouchEvent: X = " + this.X + "Y = " + this.Y);

        invalidate();
        return true;
    }

    private int mCurrentMode = MODE_NORMAL;

    public void setMode(int mode) {
        this.mCurrentMode = mode;
    }

    public int getCurrentMode() {
        return mCurrentMode;
    }
}
