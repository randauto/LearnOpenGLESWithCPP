package com.learn.proandroidkotlin.model

import android.opengl.GLES20
import com.learn.proandroidkotlin.render.MyRender
import java.nio.ByteBuffer
import java.nio.ByteOrder
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
        val fragmentShader = MyRender.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // create empty OpenGL ES program
        program = GLES20.glCreateProgram()

        // add vertex and fragment shader to program
        GLES20.glAttachShader(program!!, vertexShader)
        GLES20.glAttachShader(program!!, fragmentShader)

        // creates opengl es program executables
        GLES20.glLinkProgram(program!!)

        // init vertex byte buffer for shape corrdinates
        val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4).order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()
        // add corrdinates to buffer
        vertexBuffer!!.put(triangleCoords)
        vertexBuffer!!.position(0)
    }

    fun draw() {
        GLES20.glUseProgram(program!!)

        // get handle to vertex shader's vPosition member.
        positionHandle = GLES20.glGetAttribLocation(program!!, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle!!)
        GLES20.glVertexAttribPointer(positionHandle!!, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer)

        // get handle to fragment shader's vColor member.
        colorHandle = GLES20.glGetUniformLocation(program!!, "vColor")
        // set color for drawing the triangle.
        GLES20.glUniform4fv(colorHandle!!, 1, color, 0)

        // draw the triangle.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        // disabled vertex array
        GLES20.glDisableVertexAttribArray(positionHandle!!)


    }
}