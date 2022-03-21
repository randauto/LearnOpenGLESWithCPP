package com.learn.proandroidkotlin.model

import android.opengl.GLES20
import com.bip.learnopengleswithcpp.utils.GLToolbox.loadShader
import com.learn.proandroidkotlin.render.MyRender
import java.nio.FloatBuffer

class Triangle {
    val vertexShaderCode = """
attribute vec4 vPosition;
void main() {
gl_Position = vPosition;
}
""".trimIndent()
    val fragmentShaderCode = """
precision mediump float;
uniform vec4 vColor;
void main() {
gl_FragColor = vColor;
}
""".trimIndent()

    var program: Int? = 0
    var vertexBuffer: FloatBuffer? = null
    var color = floatArrayOf(0.6f, 0.77f, 0.22f, 1.0f)

    var positionHandle: Int? = 0
    var colorHandle: Int? = 0

    val vertexCount = triangleCoords.size / COORDS_PER_VERTEX
    val vertexStride = COORDS_PER_VERTEX * 4 // 4 byte per vertex

    companion object {
        internal val COORDS_PER_VERTEX = 3
        internal var triangleCoords =
            floatArrayOf( // in counterclockwise order:
                0.0f, 0.6f, 0.0f, // top
                -0.5f, -0.3f, 0.0f, // bottom left
                0.5f, -0.3f, 0.0f // bottom right
            )
    }

    init {
        val vertexShader = MyRender.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
    }
}