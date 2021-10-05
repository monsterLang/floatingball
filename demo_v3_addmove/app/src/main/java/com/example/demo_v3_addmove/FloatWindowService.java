package com.example.demo_v3_addmove;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import  android.app.Service;

import android.util.Log;
import android.widget.Toast;
import android.os.Handler;
import android.os.IBinder;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FloatWindowService extends Service {
    private static final String TAG = "FloatWindowService";


    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        ALog.e("服务已创建");
        Toast.makeText(this, "My Service created", Toast.LENGTH_LONG).show();


    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "My Service destroy", Toast.LENGTH_LONG).show();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        // 开启定时器，每隔0.5秒刷新一次
//        if (timer == null) {
//            timer = new Timer();
//            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
//            if (isHome() && !MyWindowManager.isWindowShowing()) {
            if ( !MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.createSmallWindow(getApplicationContext());
                    }
                });
            }
            // 当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗。
//            else if (!isHome() && MyWindowManager.isWindowShowing()) {
            else if ( MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.removeSmallWindow(getApplicationContext());
//                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            }

        }

    }
//
//    /**
//     * 判断当前界面是否是桌面
//     */
//    private boolean isHome() {
//        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
//        return getHomes().contains(rti.get(0).topActivity.getPackageName());
//    }
//
//    /**
//     * 获得属于桌面的应用的应用包名称
//     *
//     * @return 返回包含所有包名的字符串列表
//     */
//    private List<String> getHomes() {
//        List<String> names = new ArrayList<String>();
//        PackageManager packageManager = this.getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
//                PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo ri : resolveInfo) {
//            names.add(ri.activityInfo.packageName);
//        }
//        return names;
//    }

}