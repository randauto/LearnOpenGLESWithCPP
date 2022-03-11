package com.bip.learnopengleswithcpp.renderer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.bip.learnopengleswithcpp.R;
import com.bip.learnopengleswithcpp.utils.LoggerConfig;
import com.bip.learnopengleswithcpp.utils.ShaderHelper;
import com.bip.learnopengleswithcpp.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AirHockeyRenderer2 implements GLSurfaceView.Renderer {
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";
    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private final FloatBuffer vertexData;
    private final Context context;

    private int program;
    private int aColorLocation;
    private int aPositionLocation;

    public AirHockeyRenderer2() {
        context = null;
        vertexData = null;
    }

    public AirHockeyRenderer2(Context context) {
        this.context = context;

        float[] tableVerticesWithTriangles = {
                // Order of coordinates: X, Y, R, G, B

                // Triangle Fan
                0f, 0f, 1f, 1f, 1f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

                // Line 1
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,

                // Mallets
                0f, -0.25f, 0f, 0f, 1f,
                0f, 0.25f, 1f, 0f, 0f

        };

        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_vertex_shader2);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader2);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper
                .compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        glUseProgram(program);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_POSITION_LOCATION.
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);

        glEnableVertexAttribArray(aPositionLocation);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_COLOR_LOCATION.
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);

        glEnableVertexAttribArray(aColorLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        // Draw the table.
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // Draw the center dividing line.
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet.
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet.
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
