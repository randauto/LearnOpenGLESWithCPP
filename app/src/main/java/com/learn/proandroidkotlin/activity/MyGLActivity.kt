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
/*        frame.addView(newView)
        val button = findViewById<View>(R.id.btnChangeColor)
        button.setOnClickListener {
            val view = findViewById<View>(R.id.myView) as OwnCustomView
            view.setFillColor(Color.GRAY)

        }*/

/*        setContentView(R.layout.activity_my_glactivity)

        val customLayout = findViewById<OwnCustomLayout>(R.id.custom_layout)
        val rnd = Random()
        for (i in 0 until 50) {
            val view = OwnCustomView(this)
            val width = rnd.nextInt(200) + 50
            val height = rnd.nextInt(100) + 100

            view.layoutParams = ViewGroup.LayoutParams(width, height)
            view.setPadding(2, 2, 2, 2)
            customLayout.addView(view)
        }*/
    }
}