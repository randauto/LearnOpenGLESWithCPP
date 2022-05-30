package com.learn.proandroidkotlin

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.MotionEvent
import com.learn.proandroidkotlin.render.MyRender
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay

class MyGLSurfaceView(context: Context?) : GLSurfaceView(context) {
    var renderer: MyRender? = null
    var supports3x = false
    var minVers = 0

    init {
        fetchVersion()
        setEGLContextClientVersion(2)
        setEGLContextFactory()
        renderer = MyRender()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    private fun fetchVersion() {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val confiInfo = activityManager.deviceConfigurationInfo
        val vers = confiInfo.glEsVersion
        supports3x = vers.split(".")[0] == "3"
        minVers = vers.split(".")[1].toInt()

        Log.i("LOG", "Supports Opengl 3.x = $supports3x")
        Log.i("LOG", "Opengl minor version = $minVers")

    }

    private fun setEGLContextFactory() {
        val EGL_CONTEXT_CLIENT_VERSION = 0x3098

        class ContextFactory : EGLContextFactory {
            override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext {
                val attrib_list = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
                return egl!!.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list)
            }

            override fun destroyContext(egl: EGL10, display: EGLDisplay, context: EGLContext) {
                egl.eglDestroyContext(display, context)
            }

        }

        setEGLContextFactory(ContextFactory())
    }
    private val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f
    private var previousX: Float = 0f
    private var previousY: Float = 0f

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        val x: Float = e!!.x
        val y: Float = e.y

        when (e.action) {
            MotionEvent.ACTION_MOVE -> {

                var dx: Float = x - previousX
                var dy: Float = y - previousY

                // reverse direction of rotation above the mid-line
                if (y > height / 2) {
                    dx *= -1
                }

                // reverse direction of rotation to left of the mid-line
                if (x < width / 2) {
                    dy *= -1
                }

                renderer!!.angle += (dx + dy) * TOUCH_SCALE_FACTOR
                requestRender()
            }
        }

        previousX = x
        previousY = y
        return true
    }
}