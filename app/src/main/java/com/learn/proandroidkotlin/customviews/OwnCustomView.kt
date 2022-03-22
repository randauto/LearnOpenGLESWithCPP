package com.learn.proandroidkotlin.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.bip.learnopengleswithcpp.R
import java.lang.Exception

class OwnCustomView : View {
    private var backgroundPain: Paint? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        // read attribute
        var fillColor = Color.RED
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.OwnCustomView, 0, 0)
        try {
            if (ta.hasValue(R.styleable.OwnCustomView_fillColor)) {
                fillColor = ta.getColor(R.styleable.OwnCustomView_fillColor, Color.RED)
            }
            backgroundPain = Paint().apply {
                color = fillColor
                style = Paint.Style.FILL
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            ta.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        backgroundPain?.let { canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), it) }
        super.onDraw(canvas)
    }

    fun setFillColor(color: Int) {
        backgroundPain?.color = color
        invalidate()
    }

}