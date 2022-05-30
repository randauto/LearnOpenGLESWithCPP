package com.learn.proandroidkotlin.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.bip.learnopengleswithcpp.R

class DrawBitmapCustom : View {
    private var backgroundBitmap: Bitmap? = null
    private var bitmapSource : Rect? = null
    private var bitmapDest : Rect? = null
    private var matrixBitmap : Matrix? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        backgroundBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.gai1)
        bitmapSource = Rect()

        bitmapSource!!.top = 0
        bitmapSource!!.left = 0

        if (backgroundBitmap != null) {
            bitmapSource!!.left = backgroundBitmap!!.width / 2
            bitmapSource!!.right = backgroundBitmap!!.width
            bitmapSource!!.bottom = backgroundBitmap!!.height
        }

        bitmapDest = Rect()

        matrixBitmap = Matrix()
        matrixBitmap!!.postScale(0.5f, 0.5f)
        matrixBitmap!!.postTranslate(0f, 200f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (backgroundBitmap != null) {
            // draw right half of bitmap root.
            bitmapDest!!.right = width
            bitmapDest!!.bottom = height
//            canvas.drawBitmap(backgroundBitmap!!, bitmapSource, bitmapDest!!, null)
            canvas.drawBitmap(backgroundBitmap!!, matrixBitmap!!, null)
        }

    }
}