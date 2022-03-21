package com.bip.learnopengleswithcpp.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.bip.learnopengleswithcpp.GameLibJNIWrapper;
import com.bip.learnopengleswithcpp.R;
import com.bip.learnopengleswithcpp.model.Square;
import com.bip.learnopengleswithcpp.utils.GLToolbox;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EffectsRenderer implements GLSurfaceView.Renderer {
    private Bitmap photo;
    private int photoWidth;
    private int photoHeight;

    private EffectContext effectContext;
    private Effect effect;


    public EffectsRenderer(Context context) {
        super();
        photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.gai1);
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();
    }

    private int textures[] = new int[2];
    private Square square;

    private void generateSquare() {
        GLES20.glGenTextures(2, textures, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLToolbox.initTexParams();
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, photo, 0);
        square = new Square();
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GameLibJNIWrapper.on_surface_created();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GameLibJNIWrapper.on_surface_changed(width, height);
        generateSquare();
    }

    private void grayScaleEffect() {
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GameLibJNIWrapper.on_draw_frame();
        if (effectContext == null) {
            effectContext = EffectContext.createWithCurrentGlContext();
        }
        if (effect != null) {
            effect.release();
        }

        if (EffectFactory.isEffectSupported(EffectFactory.EFFECT_GRAYSCALE)) {
            grayScaleEffect();
        }

        square.draw(textures[1]);
    }
}
