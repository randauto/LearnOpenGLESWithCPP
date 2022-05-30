package com.learn.proandroidkotlin.model

import android.opengl.GLES20
import com.learn.proandroidkotlin.render.MyRender
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle {
    private val vertexShaderCode =
    // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                // the matrix must be included as a modifier of gl_Position
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"
    val fragmentShaderCode = """
precision mediump float;
uniform vec4 vColor;
void main() {
gl_FragColor = vColor;
}
""".trimIndent()

    var program: Int? = 0
    var vertexBuffer: FloatBuffer? = null
    var colorRed = floatArrayOf(125f, 0.77f, 0.22f, 0.5f)
    var colorBlue = floatArrayOf(0f, 0.77f, 0.22f, 0.5f)

    var positionHandle: Int? = 0
    var colorHandle: Int? = 0

    val vertexCount = triangleCoordsBlue.size / COORDS_PER_VERTEX
    val vertexStride = COORDS_PER_VERTEX * 4 // 4 byte per vertex

    companion object {
        internal val COORDS_PER_VERTEX = 3
        internal var triangleCoordsBlue =
            floatArrayOf( // in counterclockwise order:
                0.0f, 0.6f, 0.0f, // top
                -0.5f, -0.3f, 0.0f, // bottom left
                0.5f, -0.3f, 0.0f // bottom right
            )

        internal var triangleCoordsRed =
            floatArrayOf( // in counterclockwise order:
                0.0f, 0.6f, 0.0f, // top
                -0.25f, -0.25f, 0.0f, // bottom left
                0.25f, -0.25f, 0.0f // bottom right
            )
    }

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0

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
        val bb = ByteBuffer.allocateDirect((triangleCoordsBlue.size * 4) * 2)
            .order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()
        // add corrdinates to buffer
        vertexBuffer!!.put(triangleCoordsBlue)
        vertexBuffer!!.put(triangleCoordsRed)
        vertexBuffer!!.position(0)
    }

    fun draw(vPMatrix: FloatArray) {
        GLES20.glUseProgram(program!!)
        drawBlueTriange(vPMatrix)
        // disabled vertex array
        GLES20.glDisableVertexAttribArray(positionHandle!!)

    }

    private fun drawBlueTriange(mvpMatrix: FloatArray) {
        // get handle to vertex shader's vPosition member.
        positionHandle = GLES20.glGetAttribLocation(program!!, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle!!)
        GLES20.glVertexAttribPointer(
            positionHandle!!,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )

        // get handle to fragment shader's vColor member.
        colorHandle = GLES20.glGetUniformLocation(program!!, "vColor")
        // set color for drawing the triangle.
        GLES20.glUniform4fv(colorHandle!!, 1, colorBlue, 0)
        GLES20.glLineWidth(10.0f)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(program!!, "uMVPMatrix")

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)
        // draw the triangle.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
    }
}