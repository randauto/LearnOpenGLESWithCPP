package com.bip.learnopengleswithcpp.activity;

import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bip.learnopengleswithcpp.R;
import com.bip.learnopengleswithcpp.renderer.GLLayer;

public class ShaderFilterActivity extends AppCompatActivity implements View.OnClickListener {

    GLSurfaceView mView;
    private Button mItemCapture0;
    private Button mItemCapture1;
    private Button mItemCapture2;
    private Button mItemCapture3;
    private Button mItemCapture4;
    private Button mItemCapture5;
    private Button mItemCapture6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_shader_filter);

        mView = findViewById(R.id.glSurfaceView);
        mView.setEGLContextClientVersion(2);
        mView.setRenderer(new GLLayer(this));
        mView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


        mItemCapture0 = findViewById(R.id.Original);
        mItemCapture1 = findViewById(R.id.Warm);
        mItemCapture2 = findViewById(R.id.VanGogh);
        mItemCapture3 = findViewById(R.id.Monet);
        mItemCapture4 = findViewById(R.id.Pop);
        mItemCapture5 = findViewById(R.id.Manga);
        mItemCapture6 = findViewById(R.id.Blur);

        mItemCapture0.setOnClickListener(this);
        mItemCapture1.setOnClickListener(this);
        mItemCapture2.setOnClickListener(this);
        mItemCapture3.setOnClickListener(this);
        mItemCapture4.setOnClickListener(this);
        mItemCapture5.setOnClickListener(this);
        mItemCapture6.setOnClickListener(this);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onResume() {
        super.onResume();
        mView.onResume();

    }

    protected void onPause() {
        super.onPause();
        mView.onPause();
    }
    /*
     */

    /**
     * menu button setup
     *//*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mItemCapture0 = menu.add("Original");
        mItemCapture1 = menu.add("Warm");
        mItemCapture2 = menu.add("Van Gogh");
        mItemCapture3 = menu.add("Monet");
        mItemCapture4 = menu.add("Pop");
        mItemCapture5 = menu.add("Manga");
        mItemCapture6 = menu.add("Blur");
        return true;

    }*/
    @Override
    public void onClick(View v) {
        int item = v.getId();

        if (item == R.id.Original) {
            GLLayer.shader_selection = 0;
        }
        if (item == R.id.Warm) {
            GLLayer.shader_selection = GLLayer.WARM;
        }
        if (item == R.id.VanGogh) {
            GLLayer.shader_selection = GLLayer.VAN_GOGH;
        }
        if (item == R.id.Monet) {
            GLLayer.shader_selection = GLLayer.MONET;
        }
        if (item == R.id.Pop) {
            GLLayer.shader_selection = GLLayer.POP;
        }
        if (item == R.id.Manga) {
            GLLayer.shader_selection = GLLayer.MANGA;
        }
        if (item == R.id.Blur) {
            GLLayer.shader_selection = GLLayer.BLUR;
        }

        // request render when change type of filter.
        mView.requestRender();
    }
}
