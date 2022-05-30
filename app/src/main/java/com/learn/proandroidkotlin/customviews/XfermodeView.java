package com.learn.proandroidkotlin.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.learn.proandroidkotlin.utils.ScreenUtil;

public class XfermodeView extends View {

    private PorterDuffXfermode porterDuffXfermode;
    private static PorterDuff.Mode PD_MODE = PorterDuff.Mode.SCREEN;
    private int screenW, screenH;
    private int width = 200;
    private int height = 200;
    private Bitmap srcBitmap, dstBitmap;

    public XfermodeView(Context context) {
        super(context);
        init(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        screenW = ScreenUtil.getScreenW(context);
        screenH = ScreenUtil.getScreenH(context);

        porterDuffXfermode = new PorterDuffXfermode(PD_MODE);
        srcBitmap = makeSrc(width, height);
        dstBitmap = makeDst(width, height);
    }

    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.GREEN);
        c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
        return bm;
    }

    private Bitmap makeSrc(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.YELLOW);

        canvas.drawRect(width / 3, height / 3, width * 19 / 20, height * 19 / 20, p);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawBitmap(srcBitmap, (screenW / 3 - width) / 2, (screenH / 2 - height) / 2, paint);
        canvas.drawBitmap(dstBitmap, (screenW / 3 - width) / 2 + screenW / 3, (screenH / 2 - height) / 2, paint);

        int sc = canvas.save();

        canvas.drawBitmap(dstBitmap, (screenW / 3 - width) / 2 + screenW / 3 * 2, (screenH / 2 - height) / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(srcBitmap, (screenW / 3 - width) / 2 + screenW / 3 * 2, (screenH / 2 - height) / 2, paint);
        paint.setXfermode(null); // reset mode.

        canvas.restoreToCount(sc);
    }
}
