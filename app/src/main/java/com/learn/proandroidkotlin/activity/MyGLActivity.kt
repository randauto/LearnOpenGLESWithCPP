package com.learn.proandroidkotlin.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.bip.learnopengleswithcpp.R
import com.learn.proandroidkotlin.MyGLSurfaceView

class MyGLActivity : AppCompatActivity() {
    var glView: MyGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glView = MyGLSurfaceView(this)
//        val newView = OwnCustomView(this)
        val frame = FrameLayout(this)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        frame.layoutParams = layoutParams
        frame.addView(glView)
        setContentView(frame)
    }

    override fun onPause() {
        super.onPause()
        glView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        glView?.onResume()
    }
}