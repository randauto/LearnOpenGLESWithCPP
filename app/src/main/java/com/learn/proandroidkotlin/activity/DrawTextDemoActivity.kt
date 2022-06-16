package com.learn.proandroidkotlin.activity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.icu.text.TimeZoneFormat
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bip.learnopengleswithcpp.R
import com.caverock.androidsvg.SVG

class DrawTextDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_text_demo)

        val svg = SVG.getFromString("<svg id=\"design\" viewBox=\"0 0 300 200\">\n" +
                "    <path class=\"motion\" d=\"\n" +
                "      M  0,  0\n" +
                "      C  0,  0  99,  1  99, 89\n" +
                "      L 99,111\n" +
                "      C 99,130  75,112  99,112\n" +
                "      C103,112 100,112 106,112\n" +
                "      C121,112 121, 89 105, 89\n" +
                "      C 88, 89 102, 89  99, 89\n" +
                "      C  0, 89  80,103 123,103\n" +
                "      C128,103 135,103 136,103\n" +
                "      C155,103 137,140 137,103\n" +
                "      C137, 92 123, 93 123,103\n" +
                "      C123,115 135,113 137,110\n" +
                "      C152, 86 103, 87 143,110\n" +
                "      C152,116 157,106 147,103\n" +
                "      C140,102 143, 93 152, 96\n" +
                "      C206,118 159.5,50 159,89\n" +
                "      L159,112\n" +
                "      C159,180 117, 93 168,118\n" +
                "      C176,122 180,116 180,115\n" +
                "      C183,107 169,101 168, 90\n" +
                "      C167, 77 180, 67 180, 95\n" +
                "      L180,103\n" +
                "      C180,115 166,115 166,103\n" +
                "      C166, 92 180, 94 180,102\n" +
                "      C180,180 188,125 188,112\n" +
                "      C188,110 188,100 188, 96\n" +
                "      C188, 91 189, 80 194, 91\n" +
                "      C200,101 186,134 188,106\n" +
                "      C189, 93 201, 93 201,100\n" +
                "      C201,105 201,105 201,112\n" +
                "      C201,130 220,160 300, 80\n" +
                "    \"></path>\n" +
                "  </svg>")

        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        svg.documentHeight = 500f
        svg.documentWidth = 500f

        svg.renderToCanvas(canvas)
        val image = findViewById<ImageView>(R.id.imageView)
        image.setImageBitmap(bitmap)
    }
}