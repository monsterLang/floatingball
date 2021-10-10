package com.example.demo_v4_qxdmlog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
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

//    //触摸监听器
//    GestureDetector mGestureDetector;
//    //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
//    private int mTouchStartX,mTouchStartY,mTouchCurrentX,mTouchCurrentY;
//    //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
//    private int mStartX,mStartY,mStopX,mStopY;
//    private boolean isMove;//判断悬浮窗是否移动

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//
//        int action = event.getAction();
//
//        switch(action){
//            case MotionEvent.ACTION_DOWN:
//                isMove = false;
//                mTouchStartX = (int)event.getRawX();
//                mTouchStartY = (int)event.getRawY();
//                mStartX = (int)event.getX();
//                mStartY = (int)event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mTouchCurrentX = (int) event.getRawX();
//                mTouchCurrentY = (int) event.getRawY();
//                wmParams.x += mTouchCurrentX - mTouchStartX;
//                wmParams.y += mTouchCurrentY - mTouchStartY;
//                mWindowManager.updateViewLayout(mlayout, wmParams);
//
//                mTouchStartX = mTouchCurrentX;
//                mTouchStartY = mTouchCurrentY;
//                break;
//            case MotionEvent.ACTION_UP:
//                mStopX = (int)event.getX();
//                mStopY = (int)event.getY();
//                //System.out.println("|X| = "+ Math.abs(mStartX - mStopX));
//                //System.out.println("|Y| = "+ Math.abs(mStartY - mStopY));
//                if(Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1){
//                    isMove = true;
//                }
//                break;
//        }
//        return mGestureDetector.onTouchEvent(event);  //此处必须返回false，否则OnClickListener获取不到监听


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
