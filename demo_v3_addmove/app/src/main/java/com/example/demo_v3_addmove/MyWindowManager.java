package com.example.demo_v3_addmove;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MyWindowManager {


    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 小悬浮窗View的参数
     */
    private static WindowManager.LayoutParams smallWindowParams;


    /**
     * 小悬浮窗View的实例
     */
    private static FloatWindowSmallView smallWindow;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {

        // get screen width/height
        WindowManager windowManager = getWindowManager(context);
        DisplayMetrics display_n = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(display_n);
        int screenWidth = display_n.widthPixels;
        int screenHeight = display_n.heightPixels;


        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(context);

            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                //窗口显示权限
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;   //android 8.0 以后使用 https://blog.csdn.net/mai763727999/article/details/78983375?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163307717816780264095751%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=163307717816780264095751&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-78983375.pc_search_all_es&utm_term=TYPE_APPLICATION_OVERLAY&spm=1018.2226.3001.4187
                //色彩信息
                smallWindowParams.format = PixelFormat.RGBA_8888;
                //窗口弹出是其他区域是否可触摸
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                //窗口长宽
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                //子控件，居左居上
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                //起始位置
                smallWindowParams.x = screenWidth / 2;  // 屏幕中间
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }
    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }


    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     *            必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null ;
    }

}
