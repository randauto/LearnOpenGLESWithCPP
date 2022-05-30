package com.learn.proandroidkotlin.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bip.learnopengleswithcpp.R;

public class CustomViewWithCamera extends View {
    private Paint paint;
    private Bitmap image;

    private Camera camera;
    private int degree;

    public CustomViewWithCamera(Context context) {
        super(context);
        init();
    }

    public CustomViewWithCamera(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewWithCamera(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        image = BitmapFactory.decodeResource(getResources(), R.drawable.gai1);
        degree = 30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        method2(canvas);
//        method1(canvas);
        rotateCameraX(canvas);
    }

    private void rotateCameraX(Canvas canvas) {
        canvas.save();

        camera.save();

        camera.rotateX(60);
        // default position is (0, 0, -8)
        camera.setLocation(0, 0, -20);
        canvas.translate(image.getWidth() / 2, image.getHeight() / 2);
        camera.applyToCanvas(canvas);
        canvas.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        camera.restore();

        canvas.drawBitmap(image, 0, 0, paint);
        canvas.restore();
    }

    private void method2(Canvas canvas) {
        canvas.save();

        camera.save();

        camera.rotateX(60);
        // default position is (0, 0, -8)
        camera.setLocation(0, 0, -20);
        canvas.translate(image.getWidth() / 2, image.getHeight() / 2);
        camera.applyToCanvas(canvas);
        canvas.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        camera.restore();

        canvas.drawBitmap(image, 0, 0, paint);
        canvas.restore();
    }

    private void method1(Canvas canvas) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - imageWidth / 2;
        int y = centerY - imageHeight / 2;

        // top part
        canvas.save();
//        canvas.clipRect(0, 0, getWidth(), centerY);
//        canvas.drawBitmap(image, x, y, paint);
//        canvas.restore();
//
//        // bottom part
//        canvas.save();

//        if (degree < 90) {
//            canvas.clipRect(0, centerY, getWidth(), getHeight());
//        } else {
//            canvas.clipRect(0, 0, getWidth(), centerY);
//        }

//        camera.save();
//        camera.rotateX(degree);
//        canvas.translate(centerX, centerY);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-centerX, -centerY);
//        camera.restore();
        camera.save();
        camera.rotateY(45);
//        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(image, x, y, paint);
        canvas.restore();
    }
}
