package com.learn.proandroidkotlin.activity;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bip.learnopengleswithcpp.R;
import com.learn.proandroidkotlin.customviews.MyViewTouch;

public class PathEffectActivity extends AppCompatActivity {
    private ImageView imageView;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mode = NONE;

    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oriDis = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_effect);

        setOnTouchImageView();

        initView();
    }

    private void initView() {
        Button btnClear = findViewById(R.id.btnClear);
        MyViewTouch myViewTouch = findViewById(R.id.myViewTouch);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewTouch.getCurrentMode() == MyViewTouch.MODE_CLEAR) {
                    myViewTouch.setMode(MyViewTouch.MODE_NORMAL);
                } else {
                    myViewTouch.setMode(MyViewTouch.MODE_CLEAR);
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchImageView() {

        imageView = findViewById(R.id.imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        matrix.set(view.getImageMatrix());
                        savedMatrix.set(matrix);

                        startPoint.set(event.getX(), event.getY());
                        mode = DRAG;

                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        oriDis = distance(event);
                        if (oriDis > 10f) {
                            savedMatrix.set(matrix);
                            midPoint = middle(event);
                            mode = ZOOM;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                        } else {
                            float newDist = distance(event);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oriDis;
                                matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                            }
                        }
                        break;
                }

                view.setImageMatrix(matrix);
                return true;
            }
        });
    }

    private PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }
}