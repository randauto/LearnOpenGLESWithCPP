package com.bip.learnopengleswithcpp.activity;


import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bip.learnopengleswithcpp.renderer.EffectsRenderer;

public class OpenGLCPPActivity extends AppCompatActivity {
    GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new EffectsRenderer(this));
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        rendererSet = true;
        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null && rendererSet) {
            glSurfaceView.onPause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null && rendererSet) {
            glSurfaceView.onResume();
        }
    }
}