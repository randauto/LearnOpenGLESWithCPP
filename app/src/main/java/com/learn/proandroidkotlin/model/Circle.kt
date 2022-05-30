package com.learn.proandroidkotlin.model

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


class Circle {
    private var mProgram = 0
    private var mPositionHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mMVPMatrixHandle: Int = 0

    private var mVertexBuffer: FloatBuffer? = null

    private val vertices = FloatArray(364 * 3)

    var color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 0.5f)
    var colorRed = floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f)

    private val vertexShaderCode = "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}"

    private val fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"

    init {
        vertices[0] = 0f
        vertices[1] = 0f
        vertices[2] = 0f
        for (i in 1..363) {
            vertices[i * 3 + 0] = (0.5 * Math.cos(3.14 / 180 * i.toFloat())).toFloat()
            vertices[i * 3 + 1] = (0.5 * Math.sin(3.14 / 180 * i.toFloat())).toFloat()
            vertices[i * 3 + 2] = 0f
        }
        Log.v("Thread", "" + vertices[0] + "," + vertices[1] + "," + vertices[2])
        val vertexByteBuffer: ByteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        vertexByteBuffer.order(ByteOrder.nativeOrder())
        mVertexBuffer = vertexByteBuffer.asFloatBuffer()
        mVertexBuffer!!.put(vertices)
        mVertexBuffer!!.position(0)
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        mProgram = GLES20.glCreateProgram() // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader) // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader) // add the fragment shader to program
        GLES20.glLinkProgram(mProgram)
    }

    fun loadShader(type: Int, shaderCode: String?): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }


    fun draw(mvpMatrix: FloatArray?) {
        GLES20.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle)

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            mPositionHandle, 3,
            GLES20.GL_FLOAT, false, 12, mVertexBuffer
        )

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")


        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)


        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 180)
        // Set color for drawing the triangle
//        GLES20.glUniform4fv(mColorHandle, 1, colorRed, 0)
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 350)
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
}