package com.example.demo_v4_qxdmlog;
//
////v2
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//import android.view.WindowManager;
//import android.util.DisplayMetrics;
//
//import android.content.Intent;
//
//import android.widget.Toast;
//import android.os.IBinder;
//import android.content.Intent;
//import  android.app.Service;
//import android.content.Context;
//
//import com.example.my_application1.FloatWindowService;
//
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    private WindowManager mWindowManager;
//
//    private Button btn_openfloatball;
//    private Button btn_closefloatball;//closefloatballbtn
//    private Button btn_closeapp;
//    private TextView text;
//    private TextView text_width;
//    private TextView text_height;
//    private int mWidth,mHeight;   //屏幕的宽高
//    private String string_mwidth,string_mheight;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initUI();
//
//    }
//
//    private void start_floatball_service() {
//        Intent intent_start_floatball_service = new Intent(MainActivity.this, FloatWindowService.class);
//        startService(intent_start_floatball_service);
//    }
//    private void stop_floatball_service() {
//        Intent intent_stop_floatball_service = new Intent(MainActivity.this, FloatWindowService.class);
//        stopService(intent_stop_floatball_service);
//    }
//
//    private void initUI() {
//        // TODO Auto-generated method stub
//        btn_openfloatball = findViewById(R.id.openfloatballbtn);
//        btn_openfloatball.setOnClickListener(new openfloatballListener());
//        btn_closefloatball = findViewById(R.id.closefloatballbtn);
//        btn_closefloatball.setOnClickListener(new closefloatballListener());
//        btn_closeapp = findViewById(R.id.exitbtn);
//        btn_closeapp.setOnClickListener(new closeappListener());
//        text = (TextView)findViewById(R.id.textView);
//        text.setText("init");
//
//        //get window manager
//        mWindowManager = (WindowManager) this.getWindowManager();
////        if (mWindowManager == null)
////        {
////            text.setText("no window");
////        }
////        else
////        {
////            text.setText("have window");
////        }
//
////        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics display = new DisplayMetrics();
//        mWindowManager.getDefaultDisplay().getMetrics(display);
////        if (display == null)
////        {
////            text.setText("no display");
////        }
////        else
////        {
////            text.setText("have display");
////        }
//
//        mWidth = display.widthPixels;
//        mHeight = display.heightPixels;
////        mWidth=mWindowManager.getDefaultDisplay().getWidth();
////        mHeight = this.getResources().getDisplayMetrics().heightPixels;
//
//        //直接自动转int为string，使用valueof失败原因？
////        string_mwidth.valueOf(mWidth);
////        string_mheight.valueOf(mHeight);
//        text_width = (TextView)findViewById(R.id.WidthText);
//        text_width.setText("宽度 "+ mWidth);
//        text_height = (TextView)findViewById(R.id.HeightText);
//        text_height.setText("高度 "+ mHeight);
//
//    }
//    public class openfloatballListener implements OnClickListener {
//        @Override
//        public void onClick(View arg0) {
//            // TODO Auto-generated method stub
//
//            // display status in textview "button on"
//            text = (TextView)findViewById(R.id.textView);
//            text.setText("button on");
//
//            Toast.makeText(getApplicationContext(), "openfloatball", Toast.LENGTH_LONG).show();
//
//            // create floatball_service
//            start_floatball_service();
//
//
//            //create floatbutton
////            Button floatingButton = new Button(this);
////            floatingButton.setText("button");
////
////            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
////                    WindowManager.LayoutParams.WRAP_CONTENT,
////                    WindowManager.LayoutParams.WRAP_CONTENT,
////                    0, 0,
////                    PixelFormat.TRANSPARENT
////            );
//
//            //启动悬浮窗口关闭本窗口
////            startService(intent);
////            finish();
//        }
//    }
//    public class closefloatballListener implements OnClickListener {
//        @Override
//        public void onClick(View arg0) {
//            // TODO Auto-generated method stub
//            text = (TextView)findViewById(R.id.textView);
//            text.setText("button off");
//
//            stop_floatball_service();
//
//        }
//    }
//    public class closeappListener implements OnClickListener {
//        @Override
//        public void onClick(View arg0) {
//            // TODO Auto-generated method stub
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
//    }
//
//
//
//}

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN = "MainActivity";

    // 1 display view params
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams viewparams;
    private WindowManager.LayoutParams viewparams_parent;
    private WindowManager.LayoutParams viewparams_layout;
    private FloatView_youkai ball;

    // 2 ball status change params
    private boolean ball_status;
    private boolean ball_status_add;

    // 3 buttons to display floatingball  ---- draw circular
    private Button btn_openfloatballview_youkai;
    private Button btn_closefloatballview_youkai;

    // 4 buttons to display floatingball   ---- layout
    private Button btn_openlayoutview;
    private Button btn_closelayoutview;
    LayoutInflater inflater;
    LinearLayout mlayout;      // floating text (layout to replace ball)

    // send cmd func1 socket -- ok
    protected Executor mExecutor = null;
    protected Executor mExecutor_stop = null;

    private TextView text_layoutview;       // use to display current is recording/off

    private TextView textview_cmd_current;  // show current cmd
    private EditText textview_cmd_send;     // edit view for set start cmd
    private String cmd_start;
    private String cmd_stop;

    private boolean status_layoutview;  //记录layout球是否显示，显示为true
    private boolean status_layoutview_text;

    // send cmd func2 -- error
    private CommandExecution cmd_exec;

    // 8 move layout view
    // move event minor
    GestureDetector mGestureDetector;

    //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private int mTouchStartX,mTouchStartY,mTouchCurrentX,mTouchCurrentY;
    //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private int mStartX,mStartY,mStopX,mStopY;
    private boolean isMove;//判断悬浮窗是否移动



    // 5 create service
    private Button btn_openfloatball_service;
    private Button btn_closefloatball_service;


    // status display
    private TextView text;

    // 6 device display params
    private TextView text_width;
    private TextView text_height;
    private int mWidth, mHeight;   //屏幕的宽高

    // 7 close app
    private Button btn_closeapp;



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

        //get window manager
        mWindowManager = (WindowManager) this.getWindowManager();
        if (mWindowManager == null)
            Toast.makeText(getApplicationContext(), "no window", Toast.LENGTH_SHORT).show();

        // 使用layout布局创建悬浮textview
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

        cmd_start = null;
        cmd_stop = null;

        mGestureDetector = new GestureDetector(this, new MyOnGestureListener());
//        //设置监听器
//        text_layoutview.setOnTouchListener(new FloatingListener());


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


    // 8 move
    private class FloatingListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View arg0, MotionEvent event) {

            int action = event.getAction();
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    mTouchStartX = (int)event.getRawX();
                    mTouchStartY = (int)event.getRawY();
                    mStartX = (int)event.getX();
                    mStartY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentX = (int) event.getRawX();
                    mTouchCurrentY = (int) event.getRawY();
                    viewparams_layout.x += mTouchCurrentX - mTouchStartX;
                    viewparams_layout.y += mTouchCurrentY - mTouchStartY;
                    mWindowManager.updateViewLayout(mlayout, viewparams_layout);

                    mTouchStartX = mTouchCurrentX;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopX = (int)event.getX();
                    mStopY = (int)event.getY();
                    //System.out.println("|X| = "+ Math.abs(mStartX - mStopX));
                    //System.out.println("|Y| = "+ Math.abs(mStartY - mStopY));
                    if(Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1){
                        isMove = true;
                    }
                    break;
            }
            return mGestureDetector.onTouchEvent(event);  //此处必须返回false，否则OnClickListener获取不到监听
        }


    }



    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!isMove) {

                Toast.makeText(getApplicationContext(), "你点击了悬浮窗", Toast.LENGTH_SHORT).show();

                System.out.println("onclick");
            }
            return super.onSingleTapConfirmed(e);
        }
    }

    // parent 绘制圆形
    private void remove_view() {
        mWindowManager.removeView(ball);
        ball_status_add = false;
    }

    private void add_view() {
        mWindowManager.addView(ball, viewparams_parent);
        ball_status_add = true;
    }

    // 切换parent圆颜色
    // 可以增加命令去抓取log
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

    // layout 显示text view
    private void layoutview_on(){
        text_layoutview= mlayout.findViewById(R.id.layoutview_text);
        text_layoutview.setTextColor(Color.GREEN);
        text_layoutview.setText("Recording");
        status_layoutview_text = true;

        startRecordQxdmLog();
    }

    private void layoutview_off(){
        text_layoutview= mlayout.findViewById(R.id.layoutview_text);
        text_layoutview.setTextColor(Color.RED);
        text_layoutview.setText("Record off");
        status_layoutview_text = false;
        stopRecordQxdmLog();
    }


    public  String getDateTimeString() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int year = (cal.get(Calendar.YEAR) + 1900) % 100;
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String str = null;
        str = String.format("%02d%02d%02d_%02d%02d%02d", year, month, date, hour, minute, second);

        return str;
    }




    private void startRecordQxdmLog(){
//        StringBuffer cmdBuf = new StringBuffer();
//        cmdBuf.append("diag_mdlog");
//        cmdBuf.append(" -f ");
//        cmdBuf.append("/sdcard/default_V2.6_auddsp_btfmwlan_ril1.cfg");

        Toast.makeText(getApplicationContext(), "start record qxdm log", Toast.LENGTH_SHORT).show();

        // send start record cmd
        startQxdmRecording();
    }

    private void startQxdmRecording(){
        // 1. set default start cmd
        StringBuffer default_start_cmd = new StringBuffer();
        default_start_cmd.append("diag_mdlog -f /sdcard/default_V2.6_auddsp_btfmwlan_ril1.cfg");

        // add log save path
        String ExternalStorage = "sdcard";
        String diagLogPath = ExternalStorage + "/diag_logs/" + getDateTimeString();
        default_start_cmd.append(" -o " + diagLogPath);

        // 2. display file path
        text = (TextView) findViewById(R.id.textView);
        text.setText(diagLogPath);

        // 3. get edit text value, use to update start cmd
        String tempcmd = null;
        textview_cmd_send = (EditText) findViewById(R.id.cmd_send);
        tempcmd = textview_cmd_send.getText().toString();

        // 4. judge editcmd is not null or default
        String temp_judge_sendcmd_empty = "input start cmd";
        // ps: judge string value use equals()
        if (tempcmd.isEmpty())
        {
            // default cmd
            cmd_start = "diag_mdlog -f /sdcard/default_V2.6_auddsp_btfmwlan_ril1.cfg";
        }
        else if ( tempcmd.equals(temp_judge_sendcmd_empty)  ){
            // tips: edit text value is not changed, so use default cmd
            Toast.makeText(getApplicationContext(), "please input start cmd. \n now use default cmd", Toast.LENGTH_SHORT).show();

            cmd_start = default_start_cmd.toString();
        }
        else
        {
            // use edit cmd
            cmd_start = tempcmd;
        }
//        Toast.makeText(getApplicationContext(), tempcmd, Toast.LENGTH_SHORT).show();

        // 5. display start cmd
        textview_cmd_current = (TextView) findViewById(R.id.cmd_display);
        textview_cmd_current.setText("current_cmd : \n" + cmd_start);

        // ***send start cmd***
//        sendstartcmd(cmd_start);
    }

    private void sendstartcmd(String start_cmd){

        mExecutor = new Executor();
        boolean ret = false;

        Log.d("sendstartcmd", "startQxdmRecording");
        Log.d("sendstartcmd", "cmd : "+start_cmd);

        Toast.makeText(getApplicationContext(), start_cmd, Toast.LENGTH_SHORT).show();

        ret = mExecutor.shellAsync(start_cmd);
        if (ret) {
            Toast.makeText(getApplicationContext(), "cmd set success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "cmd set fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecordQxdmLog(){

        Toast.makeText(getApplicationContext(), "stop record QXDM log", Toast.LENGTH_SHORT).show();

        // send stop record cmd
        stopQxdmRecording();

        //func2 for send cmd -- error
//        cmd_exec = new CommandExecution();
//        String cmdBuf2 = "exit\n";
//        cmd_exec.execCommand( cmdBuf2 ,false);
    }

    private void stopQxdmRecording(){
        StringBuffer cmd_stop = new StringBuffer();
        cmd_stop.append("diag_mdlog -k");   // kill pid

        textview_cmd_current = (TextView) findViewById(R.id.cmd_display);
        textview_cmd_current.setText("current_cmd : \n"+cmd_stop.toString());

        // ***send cmd***
//        sendstopcmd(cmd_stop);
    }

    private void sendstopcmd(String stop_cmd) {
        mExecutor_stop = new Executor();
        boolean ret = false;

        Log.d("", "stopQxdmRecording");
        Log.d("", "cmd : " + stop_cmd);

        Toast.makeText(getApplicationContext(), stop_cmd, Toast.LENGTH_SHORT).show();

        ret = mExecutor_stop.shellSync(stop_cmd);
        if (ret) {
            Toast.makeText(getApplicationContext(), "cmd set success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "cmd set fail", Toast.LENGTH_SHORT).show();
        }
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
            viewparams.y = 0;
        }

        if (viewparams_parent == null){
            viewparams_parent = new WindowManager.LayoutParams();
            viewparams_parent = viewparams;
            viewparams_parent.width = 100;
            viewparams_parent.height = 100;
            viewparams_parent.x = 0;
            viewparams_parent.y = 0;
        }


        if (viewparams_layout == null) {
            viewparams_layout = new WindowManager.LayoutParams();

//            // 方法1:从viewparams获取值
//            viewparams_layout = viewparams; //不能直接赋值，否则会指向viewparams_parent
//            viewparams_layout.width = 100;
//            viewparams_layout.height = 100;
//            viewparams_layout.x = 0;
//            viewparams_layout.y = 0;

            // 方法2:直接赋值
            //窗口显示权限
            viewparams_layout.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;   //android 8.0 以后使用 https://blog.csdn.net/mai763727999/article/details/78983375?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163307717816780264095751%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=163307717816780264095751&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-78983375.pc_search_all_es&utm_term=TYPE_APPLICATION_OVERLAY&spm=1018.2226.3001.4187
            //色彩信息
            viewparams_layout.format = PixelFormat.RGBA_8888;
            //窗口弹出是其他区域是否可触摸
            viewparams_layout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                    |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

//                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                    |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

//            viewparams_layout.width = FloatWindowSmallView.viewWidth;
//            viewparams_layout.height = FloatWindowSmallView.viewHeight;
            //子控件，居左居上
            viewparams_layout.gravity = Gravity.LEFT | Gravity.TOP;

            //起始位置      // 相对于创建的窗口位置
            viewparams_layout.x = 0;
            viewparams_layout.y = 300;
            //窗口长宽，注意与view一致
            viewparams_layout.width = 200;
            viewparams_layout.height = 200;
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
        // find button to create/stop service
        btn_openfloatball_service = findViewById(R.id.openfloatballbtn);
        btn_openfloatball_service.setOnClickListener(new openfloatballListener());

        btn_closefloatball_service = findViewById(R.id.closefloatballbtn);
        btn_closefloatball_service.setOnClickListener(new closefloatballListener());

        // button close app
        btn_closeapp = findViewById(R.id.exitbtn);
        btn_closeapp.setOnClickListener(new closeappListener());

        // button for floatingball draw circular
        btn_openfloatballview_youkai = findViewById(R.id.openfloatballview_youkaibt);
        btn_openfloatballview_youkai.setOnClickListener(new openfloatballview_youkaiListener());

        btn_closefloatballview_youkai = findViewById(R.id.closefloatballview_youkaibt);
        btn_closefloatballview_youkai.setOnClickListener(new closefloatballview_youkaiListener());

        // button for floatingtext layout
        btn_openlayoutview = findViewById(R.id.bt_layoutview_open);
        btn_openlayoutview.setOnClickListener(new openlayoutviewListener());

        btn_closelayoutview = findViewById(R.id.bt_layoutview_close);
        btn_closelayoutview.setOnClickListener(new closelayoutviewListener());

        // textview for show current status
        text = (TextView) findViewById(R.id.textView);
        text.setText("init");

        // display params  height*width
        DisplayMetrics display = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(display);
        if (display == null)
            Toast.makeText(getApplicationContext(), "can't get display params", Toast.LENGTH_SHORT).show();

        mWidth = display.widthPixels;
        mHeight = display.heightPixels;

        //直接自动转int为string，使用valueof失败原因？
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

            if(status_layoutview == false){
                mWindowManager.addView(mlayout, viewparams_layout);
                status_layoutview = true;
            }

            // listen for floatingtextview touch
            mlayout.setOnClickListener(new touchlayoutview_youkaiListener());

            mlayout.setOnTouchListener(new FloatingListener());
        }
    }

    public class closelayoutviewListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            // display status in textview "button on"
            text = (TextView) findViewById(R.id.textView);
            text.setText("layoutview off");

//            if(mlayout.getVisibility() == View.VISIBLE)
            if(status_layoutview == true) {

                if (status_layoutview_text == true){
//                    stopRecordQxdmLog();
                    layoutview_off();
                }
                mWindowManager.removeView(mlayout);
                status_layoutview = false;
            }

            Toast.makeText(getApplicationContext(), "layoutview off", Toast.LENGTH_SHORT).show();


        }
    }

    public class touchlayoutview_youkaiListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (mWindowManager == null) {
//                Toast.makeText(getApplicationContext(), "no wm", Toast.LENGTH_SHORT).show();

                return;
            }

            if(status_layoutview == true){
//                Toast.makeText(getApplicationContext(), "change layoutview color", Toast.LENGTH_SHORT).show();

//                    layoutview_on();
                if(status_layoutview_text == false) {
                    layoutview_on();
//                    startRecordQxdmLog();
                }
                else if (status_layoutview_text == true){
                    layoutview_off();
//                    stopRecordQxdmLog();
                }
//                    mlayout.updateViewLayout(mlayout,viewparams_layout);
            }
            //(ball,viewparams_layout);
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
//                Toast.makeText(getApplicationContext(), "change ball color", Toast.LENGTH_SHORT).show();
//                ball.paint.setColor(Color.GREEN);//设置颜色为红
//                ball.cx = 50;
////                ball.cy = 50;
//                ball.();
//                mWindowManager.updateViewLayout(ball, viewparams_parent);

                if (ball_status == false) {
                    record_on();
                } else if (ball_status == true) {
                    record_off();
                } else
                    Log.d("", "error value");


                remove_view();
                add_view();

//                mWindowManager.updateViewLayout(ball,viewparams_parent);

//                if( mWindowManager.IsWindowVisible(ball))
//                {
//
//                }
//                if (ball.getVisibility() == View.VISIBLE) {


//                if (ball_status == true) {
//                    Toast.makeText(getApplicationContext(), "view display", Toast.LENGTH_SHORT).show();
//                }
            }
            //(ball,viewparams_parent);
        }
    }


    public class openfloatballview_youkaiListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            text = (TextView) findViewById(R.id.textView);
            text.setText("openfloatballview_youkai");
            Toast.makeText(getApplicationContext(), "openfloatballview_youkai", Toast.LENGTH_SHORT).show();

            if (ball == null)
                ball = new FloatView_youkai(getApplicationContext());

            if (mWindowManager != null) {
//                Toast.makeText(getApplicationContext(), "has wm", Toast.LENGTH_SHORT).show();
                Log.d(TAG_MAIN, "draw ball");
                if (ball_status_add == false) {
                    add_view();
                }
//                mWindowManager.addView(ball, viewparams_parent);
//                setContentView(ball);
            }

            //创建view点击事件监听,监听到点击事件就刷新
            // mWindowManager.updateViewLayout(ball,viewparams_parent);
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

            if (mWindowManager != null) {
//                Toast.makeText(getApplicationContext(), "no wm", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "has wm", Toast.LENGTH_SHORT).show();
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