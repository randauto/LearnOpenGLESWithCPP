package com.learn.proandroidkotlin.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRender implements GLSurfaceView.Renderer {
    Context context;

    public MyGLRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (gl != null) {
            gl.glClearColor(1f, 1f, 0f, 0.5f);
//            gl.glClearDepthf(1.0f);
//            gl.glEnable(GLES20.GL_DEPTH_TEST);
//            gl.glDepthFunc(GLES20.GL_LEQUAL);
//            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
//            gl.glShadeModel(GL10.GL_SMOOTH);
//            gl.glDisable(GLES20.GL_DITHER);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        if (height == 0) {
//            height = 1;
//        }
//        float aspect = width / height;
        gl.glViewport(0, 0, width, height);
//        gl.glMatrixMode(GL10.GL_PROJECTION);
//        gl.glLoadIdentity();
//        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100f);
//        gl.glMatrixMode(GL10.GL_MODELVIEW);
//        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }
}
