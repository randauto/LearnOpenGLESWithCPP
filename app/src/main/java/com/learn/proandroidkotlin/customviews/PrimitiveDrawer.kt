package com.learn.proandroidkotlin.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PrimitiveDrawer : View {
    constructor(context: Context) : super(context) {
        init(context, null)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        paint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.RED
        }
    }

    private var paint: Paint? = null
    private var rectSize: Int = 0
    private var timeStart: Long = 0L

    override fun onDraw(canvas: Canvas) {
        val angle = (System.currentTimeMillis() - timeStart) / 100f
        canvas.drawColor(Color.GRAY)// draw background view.
        canvas.save()
        canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())

        for (i in 0..15) {
            canvas.rotate(angle)
            canvas.drawRect(
                (-rectSize / 2).toFloat(),
                (-rectSize / 2).toFloat(),
                (rectSize / 2).toFloat(),
                (rectSize / 2).toFloat(),
                paint!!
            )

            canvas.scale(1.2f, 1.2f)
        }

        canvas.restore()
        invalidate()

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var smallerDimention = (right - left)
        if (bottom - top < smallerDimention) {
            smallerDimention = bottom - top
        }

        rectSize = smallerDimention / 10

        timeStart = System.currentTimeMillis()

    }
}
