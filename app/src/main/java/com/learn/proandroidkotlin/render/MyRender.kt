package com.learn.proandroidkotlin.render

import android.opengl.GLES10
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.learn.proandroidkotlin.model.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRender : GLSurfaceView.Renderer {
    var triangle: Triangle? = null


    companion object {
        fun loadShader(type: Int, shaderCode: String): Int {
            val shader = GLES20.glCreateShader(type)
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
            return shader
        }
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glEnable(GLES10.GL_CULL_FACE)
        GLES20.glCullFace(GL10.GL_BACK)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        triangle = triangle ?: Triangle()
        triangle?.draw()
    }
}