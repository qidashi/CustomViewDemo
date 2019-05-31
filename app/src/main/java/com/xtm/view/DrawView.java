package com.xtm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qidashia
 * @version 1.0
 * @date 2019/5/31
 * @description:
 */
public class DrawView extends View implements View.OnTouchListener {

    private Paint paint;
    private Path path;
    // pre x y
    int preX = 0;
    int preY = 0;
    private ExecutorService executorService;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private int width;
    private int height;


    public DrawView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        // init paint
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(18);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        // init path
        path = new Path();

        // listen touch
        setOnTouchListener(this);
        executorService = Executors.newSingleThreadExecutor();

        // for save pic
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        cacheCanvas = new Canvas(cacheBitmap);


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // cur x y
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(preX, preY, x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(cacheBitmap, 0, 0, new Paint());
        canvas.drawPath(path, paint);
    }

    public void saveBitmap() {
        if (cacheBitmap != null) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String parent = Environment.getExternalStorageDirectory().getAbsolutePath();
                    final File file = new File(parent, System.currentTimeMillis() + ".jpg");
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    OutputStream stream;
                    try {
                        stream = new FileOutputStream(file);
                        cacheBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "pic save success "
                                        + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }

}
