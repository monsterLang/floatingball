package com.example.demo_v3_addmove;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class FloatView_youkai extends View {
    private Context mcontext;
    private FloatView_youkai view;
    private boolean status_ball;

    Paint paint;
    private float cx;
    private float cy;
    private int radius = 50;


    private int screenWidth;
    private int screenHeight;

    private void setOnTouchListener() {
        if (view != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    paint.setColor(Color.GREEN);//设置颜色为红

                    Log.d("----------", "TOUCH");


                    return true;
                }
            });
        }
    }

    public FloatView_youkai(Context context) {
        super(context);

        mcontext = context;

        paint = new Paint();

        paint.setColor(Color.RED);//设置颜色为红
        paint.setAntiAlias(true);//设置锯齿状
        //填充
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20);

//        //获取屏幕参数
//        WindowManager wm  = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics display = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(display);

//        screenWidth  = display.widthPixels;
//        screenHeight  = display.heightPixels;
        //view 长宽
        screenWidth = 100;
        screenHeight = 100;
        //圆心位置
        cx = 50;
        cy = 50;

        //view 自身设置监听，好像不太行
        view = this;
//        setOnTouchListener();

        status_ball = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cx != 0 && cy != 0) {

//            paint.setColor(0x9920dde9);
            canvas.drawCircle(cx, cy, radius, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        cx = event.getX();
//        cy = event.getY();
//        if(status_ball == false) {
//            view.paint.setColor(Color.RED);//设置颜色为红
//            status_ball = true;
//        }
//        else if(status_ball == true) {
//            view.paint.setColor(Color.GREEN);//
//            status_ball = false;
//        }
//        else
//            Log.d("","error value");

        Log.i("----------", "TOUCH");
        return super.onTouchEvent(event);
    }

}
