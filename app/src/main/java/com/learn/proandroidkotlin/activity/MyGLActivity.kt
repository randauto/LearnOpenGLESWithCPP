package com.learn.proandroidkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bip.learnopengleswithcpp.R
import com.learn.proandroidkotlin.MyGLSurfaceView
import com.learn.proandroidkotlin.customviews.OwnCustomView

class MyGLActivity : AppCompatActivity() {
    var glView: MyGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glView = MyGLSurfaceView(this)
        val newView = OwnCustomView(this)
        val frame = FrameLayout(this)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        frame.layoutParams = layoutParams
        frame.addView(glView)
        frame.addView(newView)
        setContentView(R.layout.activity_my_glactivity)
    }
}