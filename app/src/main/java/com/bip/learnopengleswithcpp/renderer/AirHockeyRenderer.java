package com.bip.learnopengleswithcpp.renderer;

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

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private final Context context;

    private int program;
    private int uColorLocation;
    private int aPositionLocation;

    public AirHockeyRenderer() {
        context = null;
        vertexData = null;
    }

    public AirHockeyRenderer(Context context) {
        this.context = context;

        float[] tableVerticesWithTriangles = {
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                // Line 1
                -0.5f, 0f,
                0.5f, 0f,

                // Mallets
                0f, -0.25f,
                0f, 0.25f,

                // center rectangle.
                0.0f, 0.0f

        };

        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // x??a m??u n???n m??n h??nh v?? thi???t l???p m??u cho n?? lu??n.
        GLES20.glClearColor(0f, 0f, 0f, 0f);

        // Doc noi dung trong file shader.
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader);

        // bi??n d???ch n??

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        // link to program.
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        // use program.
        GLES20.glUseProgram(program);

        // get location of color and position.
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

        // n??i cho Opengl ?????c d??? li???u t??? v??? tr?? ?????u ti??n c???a m???ng.
        vertexData.position(0);

        // ch??? ra n??i ????? t??m position ??? ????u? ??? trong m???ng data.
        // m???i m???t ??i???m t????ng ???ng ch???a 2 th??nh ph???n floating point.
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
                false, 0, vertexData);

        // sau khi ???? li??n k???t data v???i thu???c t??nh r???i th?? c???n enabled n?? l??n.
        GLES20.glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Draw the table.
        // C???p nh???t gi?? tr??? u_Color trong code shader fragment
        GLES20.glUniform4f(uColorLocation, 0.0f, 1.0f, 1.0f, 1.0f);
        // b???t ?????u v??? h??nh ch??? nh???t, t???a ????? ?????nh b???t ?????u t??? 0 v?? ?????c 6 ??i???m. t????ng ???ng l?? 3 ?????nh.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // draw center dividing line
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        // Draw the first mallet blue.
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        // Draw the second mallet red.
        GLES20.glUniform3f(uColorLocation,1.0f, 0.0f, 0.0f );
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);

        // Draw the point at center of rectangle.
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 10, 1);
    }
}
