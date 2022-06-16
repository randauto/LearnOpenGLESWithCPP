package com.learn.proandroidkotlin.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

@RequiresApi(33)
class DrawTextView : View {
    private var path: Path? = null
    private var paintText: Paint? = null
    private var linearGradient : LinearGradient? = null
    constructor(context: Context) : super(context) {
        init(context, null)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }
    val paint = Paint()
    private fun init(context: Context, attrs: AttributeSet?) {
        path = Path()
        path!!.addCircle(200f, 200f, 50f, Path.Direction.CW)
//        path!!.addRect(RectF(300f, 300f,300f,300f), Path.Direction.CW)
        paintText = Paint().apply {
            color = Color.RED
            textSize = 50f
            isAntiAlias = true
        }

        linearGradient = LinearGradient(100f, 100f, 500f, 500f, Color.parseColor("#E91E63")
        , Color.parseColor("#2196F3"), Shader.TileMode.CLAMP)

        val fixedColorShader = RuntimeShader(COLOR_SHADER_SRC)
        fixedColorShader.setColorUniform("iColor", Color.GREEN )
        paint.shader = fixedColorShader
    }

    override fun onDrawForeground(canvas: Canvas?) {
        canvas?.let {
            canvas.drawPaint(paint) // fill the Canvas with the shader
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val text = "Le Quoc Tuan"

        val floatArrayWidths = FloatArray(text.length)
        paintText!!.getTextWidths(text, floatArrayWidths)
//        for ((index, item) in floatArrayWidths.withIndex()) {
//            Log.d("TTT", "index = $index with value = $item")
//        }

        val pathEffect = CornerPathEffect(10f)
        paintText!!.pathEffect = pathEffect
        paintText!!.maskFilter = null
        paintText!!.shader = linearGradient

        canvas.drawTextOnPath(text, path!!, 0f, 0f, paintText!!)

        canvas.drawCircle(300f,300f,200f, paintText!!)



    }

    companion object {
        private const val COLOR_SHADER_SRC =
            """uniform float2 iResolution;
   half4 main(float2 fragCoord) {
      float2 scaled = fragCoord/iResolution.xy;
      return half4(scaled, 0, 1);
   }"""
    }
}