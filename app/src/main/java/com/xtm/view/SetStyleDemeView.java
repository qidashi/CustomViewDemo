package com.xtm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SetStyleDemeView extends View {

    private Paint paint;

    public SetStyleDemeView(Context context) {
        super(context);
        init();
    }

    public SetStyleDemeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SetStyleDemeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(50);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(190,200,150,paint);
//
//        paint.setColor(0x7700FF00);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(50);
//        canvas.drawCircle(190,200,150+(50/2),paint);

        paint.setColor(0x330000FF);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(50);
        canvas.drawCircle(190,200,150,paint);
    }
}
