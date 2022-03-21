package com.learn.proandroidkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bip.learnopengleswithcpp.R
import com.learn.proandroidkotlin.MyGLSurfaceView

class MyGLActivity : AppCompatActivity() {
    var glView: MyGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glView = MyGLSurfaceView(this)
        setContentView(glView)
    }
}