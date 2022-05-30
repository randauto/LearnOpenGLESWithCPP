package com.learn.proandroidkotlin.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class DrawTextView : View {
    private var path: Path? = null
    private var paintText: Paint? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        path = Path()
//        path!!.addCircle(200f, 200f, 100f, Path.Direction.CW)
        path!!.addRect(RectF(300f, 300f,300f,300f), Path.Direction.CW)
        paintText = Paint().apply {
            color = Color.RED
            textSize = 50f
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val text = "Le Quoc Tuan"
        canvas?.drawTextOnPath(text, path!!, 0f, 0f, paintText!!)

    }
}