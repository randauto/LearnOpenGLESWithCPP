package com.learn.proandroidkotlin.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bip.learnopengleswithcpp.R


class DrawImagewPolytopolyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_imagew_polytopoly)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.gai1)
        val matrix = Matrix()
        val bitmap2 = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap2)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        val width = bitmap2.width.toFloat()
        val height = bitmap2.height.toFloat()

        val src = floatArrayOf(
            0f, 0f, // top left point
            width, 0f, // top right point
            width, height, // bottom right point
            0f, height // bottom left point
        )
        val dst = floatArrayOf(
            50f, -200f, // top left point
            width, -200f, // top right point
            width, height +200f, // bottom right point
            0f, height // bottom left point
        )
        val pointCount = 4 // number of points

// the second and fourth arguments represent the index in the src and dest arrays from which the points should start being mapped from
        matrix.setPolyToPoly(src, 0, dst, 0, pointCount)

        canvas.drawBitmap(bitmap2, matrix, null)


        val ivSecond: ImageView = findViewById<View>(R.id.imageView) as ImageView
        ivSecond.setImageBitmap(bitmap2)
    }
}