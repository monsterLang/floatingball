package com.example.demo_v3_addmove;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN = "MainActivity";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams viewparams;
    private FloatView_youkai ball;

    private boolean ball_status;
    private boolean ball_status_add;

    private Button btn_openfloatball;
    private Button btn_closefloatball;//closefloatballbtn
    private Button btn_closeapp;
    private Button btn_openfloatballview_youkai;
    private Button btn_closefloatballview_youkai;

    private Button btn_openlayoutview;
    private Button btn_closelayoutview;
    LayoutInflater inflater;
    LinearLayout mlayout;
    private boolean status_layoutview;  //记录layout球是否显示，显示为true
    private TextView text_layoutview;
    private boolean status_layoutview_text;


    private TextView text;
    private TextView text_width;
    private TextView text_height;
    private int mWidth, mHeight;   //屏幕的宽高
    private String string_mwidth, string_mheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //申请悬浮权限
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, 0);
            } else {
//                mIntent = new Intent(MainActivity.this/*需要启动service的activity*/, FloatWindowService.class/*需要启动的service*/);
//                bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);//直接启动服务方式启动
                Toast.makeText(getApplicationContext(), "open permission", Toast.LENGTH_SHORT).show();

            }
        }

        mWindowManager = (WindowManager) this.getWindowManager();

        // 使用layout布局创建悬浮球
        //得到容器，通过这个inflater来获得悬浮窗控件
        inflater = LayoutInflater.from(getApplication());
//            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlayout = (LinearLayout) inflater.inflate(R.layout.float_window_small, null);

        ball_status = false;
        ball_status_add = false;
        status_layoutview = false;
        status_layoutview_text = false;
        //定义view位置及大小参数，不需要view提前创建。
        set_viewparams();

        initUI();


//        //创建view点击事件监听,监听到点击事件就刷新
//        // mWindowManager.updateViewLayout(ball,viewparams);
//        if (ball != null)
//        {
//            Toast.makeText(getApplicationContext(), "create ball listener", Toast.LENGTH_SHORT).show();
//
//            ball.setOnClickListener(new touchfloatballview_youkaiListener());
//        }

    }

    private void remove_view() {
        mWindowManager.removeView(ball);
        ball_status_add = false;
    }

    private void add_view() {
        mWindowManager.addView(ball, viewparams);
        ball_status_add = true;
    }

    private void layoutview_on(){
        text_layoutview= mlayout.findViewById(R.id.layoutview_text);
        text_layoutview.setTextColor(Color.GREEN);
        status_layoutview_text = true;
    }

    private void layoutview_off(){
        text_layoutview= mlayout.findViewById(R.id.layoutview_text);
        text_layoutview.setTextColor(Color.RED);
        status_layoutview_text = false;
    }

    //可以增加命令去抓取log
    private void record_on() {
        ball.paint.setColor(Color.GREEN);//设置颜色为绿
//        ball.requestLayout();
        ball_status = true;

        //qxdm log start record, cmd = diag xxxx

    }

    private void record_off() {
        ball.paint.setColor(Color.RED);//设置颜色为红
//        ball.requestLayout();
        ball_status = false;

        //qxdm log stop record and save
    }


    private void set_viewparams() {
        if (viewparams == null) {
            viewparams = new WindowManager.LayoutParams();
            //窗口显示权限
            viewparams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;   //android 8.0 以后使用 https://blog.csdn.net/mai763727999/article/details/78983375?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163307717816780264095751%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=163307717816780264095751&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-78983375.pc_search_all_es&utm_term=TYPE_APPLICATION_OVERLAY&spm=1018.2226.3001.4187
            //色彩信息
            viewparams.format = PixelFormat.RGBA_8888;
            //窗口弹出是其他区域是否可触摸
            viewparams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                    |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

//                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                    |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

//            viewparams.width = FloatWindowSmallView.viewWidth;
//            viewparams.height = FloatWindowSmallView.viewHeight;
            //窗口长宽，注意与view一致
            viewparams.width = 100;
            viewparams.height = 100;
            //子控件，居左居上
            viewparams.gravity = Gravity.LEFT | Gravity.TOP;
            //起始位置      // 相对于创建的窗口位置
            viewparams.x = 200;
            viewparams.y = 200;
        }

    }

    private void start_floatball_service() {
        Intent intent_start_floatball_service = new Intent(MainActivity.this, FloatWindowService.class);
        startService(intent_start_floatball_service);
    }

    private void stop_floatball_service() {
        Intent intent_stop_floatball_service = new Intent(MainActivity.this, FloatWindowService.class);
        stopService(intent_stop_floatball_service);
    }

    private void initUI() {
        // TODO Auto-generated method stub
        btn_openfloatball = findViewById(R.id.openfloatballbtn);
        btn_openfloatball.setOnClickListener(new openfloatballListener());

        btn_closefloatball = findViewById(R.id.closefloatballbtn);
        btn_closefloatball.setOnClickListener(new closefloatballListener());

        btn_closeapp = findViewById(R.id.exitbtn);
        btn_closeapp.setOnClickListener(new closeappListener());

        // 使用parent绘制圆形悬浮球
        btn_openfloatballview_youkai = findViewById(R.id.openfloatballview_youkaibt);
        btn_openfloatballview_youkai.setOnClickListener(new openfloatballview_youkaiListener());

        btn_closefloatballview_youkai = findViewById(R.id.closefloatballview_youkaibt);
        btn_closefloatballview_youkai.setOnClickListener(new closefloatballview_youkaiListener());

        // 使用layout布局绘制悬浮球
        btn_openlayoutview = findViewById(R.id.bt_layoutview_open);
        btn_openlayoutview.setOnClickListener(new openlayoutviewListener());

        btn_closelayoutview = findViewById(R.id.bt_layoutview_close);
        btn_closelayoutview.setOnClickListener(new closelayoutviewListener());

        text = (TextView) findViewById(R.id.textView);
        text.setText("init");

        //get window manager

//        if (mWindowManager == null)
//        {
//            text.setText("no window");
//        }
//        else
//        {
//            text.setText("have window");
//        }

//        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics display = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(display);
//        if (display == null)
//        {
//            text.setText("no display");
//        }
//        else
//        {
//            text.setText("have display");
//        }

        mWidth = display.widthPixels;
        mHeight = display.heightPixels;
//        mWidth=mWindowManager.getDefaultDisplay().getWidth();
//        mHeight = this.getResources().getDisplayMetrics().heightPixels;

        //直接自动转int为string，使用valueof失败原因？
//        string_mwidth.valueOf(mWidth);
//        string_mheight.valueOf(mHeight);
        text_width = (TextView) findViewById(R.id.WidthText);
        text_width.setText("宽度 " + mWidth);
        text_height = (TextView) findViewById(R.id.HeightText);
        text_height.setText("高度 " + mHeight);

    }

    public class openlayoutviewListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            // display status in textview "button on"
            text = (TextView) findViewById(R.id.textView);
            text.setText("layoutview on");

            Toast.makeText(getApplicationContext(), "layoutview on", Toast.LENGTH_SHORT).show();

//            mWindowManager
//            viewparams
//            if(mlayout.getVisibility() != View.VISIBLE)
//            viewparams.x = 0;
//            viewparams.y = 0;
            viewparams.width=200;
            viewparams.height=200;
            if(status_layoutview == false){
                mWindowManager.addView(mlayout, viewparams);
                status_layoutview = true;
            }
        }
    }

    public class closelayoutviewListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            // display status in textview "button on"
            text = (TextView) findViewById(R.id.textView);
            text.setText("layoutview off");

            Toast.makeText(getApplicationContext(), "layoutview off", Toast.LENGTH_SHORT).show();

//            if(mlayout.getVisibility() == View.VISIBLE)
            if(status_layoutview == true) {

                if (status_layoutview_text == true){
                    layoutview_off();
                }

                mWindowManager.removeView(mlayout);
                status_layoutview = false;
            }
            mlayout.setOnClickListener(new touchlayoutview_youkaiListener());
        }
    }

    public class touchlayoutview_youkaiListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (mWindowManager == null) {
                Toast.makeText(getApplicationContext(), "no wm", Toast.LENGTH_SHORT).show();

                return;
            }

            if(status_layoutview == true){

                Toast.makeText(getApplicationContext(), "change layoutview color", Toast.LENGTH_SHORT).show();

//                    layoutview_on();
                if(status_layoutview_text == false) {
                    layoutview_on();
                }
                else if (status_layoutview_text == true){
                    layoutview_off();
                }
//                    mlayout.updateViewLayout(mlayout,viewparams);
            }
            //(ball,viewparams);
        }
    }


    public class openfloatballListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            // display status in textview "button on"
            text = (TextView) findViewById(R.id.textView);
            text.setText("button on");

            Toast.makeText(getApplicationContext(), "openfloatball", Toast.LENGTH_SHORT).show();

            // create floatball_service
            start_floatball_service();


            //create floatbutton
//            Button floatingButton = new Button(this);
//            floatingButton.setText("button");
//
//            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    0, 0,
//                    PixelFormat.TRANSPARENT
//            );

            //启动悬浮窗口关闭本窗口
//            startService(intent);
//            finish();
        }
    }

    public class closefloatballListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            text = (TextView) findViewById(R.id.textView);
            text.setText("button off");

            stop_floatball_service();

        }
    }


    public class touchfloatballview_youkaiListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (mWindowManager != null) {
                Toast.makeText(getApplicationContext(), "change ball color", Toast.LENGTH_SHORT).show();
//                ball.paint.setColor(Color.GREEN);//设置颜色为红
//                ball.cx = 50;
////                ball.cy = 50;
//                ball.();
//                mWindowManager.updateViewLayout(ball, viewparams);

                if (ball_status == false) {
                    record_on();
                } else if (ball_status == true) {
                    record_off();
                } else
                    Log.d("", "error value");


                remove_view();
                add_view();

//                mWindowManager.updateViewLayout(ball,viewparams);

//                if( mWindowManager.IsWindowVisible(ball))
//                {
//
//                }
//                if (ball.getVisibility() == View.VISIBLE) {
                if (ball_status == true) {
                    Toast.makeText(getApplicationContext(), "view display", Toast.LENGTH_SHORT).show();
                }
            }
            //(ball,viewparams);
        }
    }


    public class openfloatballview_youkaiListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            text = (TextView) findViewById(R.id.textView);
            text.setText("openfloatballview_youkai");
//            android.os.Process.killProcess(android.os.Process.myPid());
            Toast.makeText(getApplicationContext(), "openfloatballview_youkai", Toast.LENGTH_SHORT).show();

            if (ball == null)
                ball = new FloatView_youkai(getApplicationContext());

            if (mWindowManager == null) {
                Toast.makeText(getApplicationContext(), "no wm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "has wm", Toast.LENGTH_SHORT).show();
                Log.d(TAG_MAIN, "draw ball");
                if (ball_status_add == false) {
                    add_view();
                }
//                mWindowManager.addView(ball, viewparams);
//                setContentView(ball);
            }

            //创建view点击事件监听,监听到点击事件就刷新
            // mWindowManager.updateViewLayout(ball,viewparams);
            if (ball != null) {
                Toast.makeText(getApplicationContext(), "create ball listener", Toast.LENGTH_SHORT).show();

                ball.setOnClickListener(new touchfloatballview_youkaiListener());
            }
        }
    }

    public class closefloatballview_youkaiListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            text = (TextView) findViewById(R.id.textView);
            text.setText("closefloatballview_youkai");
//            android.os.Process.killProcess(android.os.Process.myPid());
            Toast.makeText(getApplicationContext(), "closefloatballview_youkai", Toast.LENGTH_SHORT).show();


//            ball = new FloatView_youkai(getApplicationContext());

            if (mWindowManager == null) {
                Toast.makeText(getApplicationContext(), "no wm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "has wm", Toast.LENGTH_SHORT).show();
                Log.d(TAG_MAIN, "draw ball");

                //如果显示了view就移除，按理说不应该用这个，也可能view不显示在最前。
//                if(ball.getVisibility() == View.VISIBLE)
                if (ball_status_add == true) {
                    remove_view();
                    if (ball_status == true) {
                        record_off();
                    }
                }
//                setContentView(ball);
            }

//            if(ball_status_add == false)
//            {
//                Log.d(TAG_MAIN, "view no display");
//                Toast.makeText(getApplicationContext(), "view no display", Toast.LENGTH_SHORT).show();
//            }
//            else if(ball_status_add == true)
//            {
//                Log.d(TAG_MAIN, "view GONE");
//                Toast.makeText(getApplicationContext(), "view GONE", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    public class closeappListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

//            if (ball.getVisibility() == View.VISIBLE)
            if (ball_status_add == true)
                mWindowManager.removeView(ball);
            //待添加判断是否显示球，若显示则移除，若不显示，则删除ball变量

            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}