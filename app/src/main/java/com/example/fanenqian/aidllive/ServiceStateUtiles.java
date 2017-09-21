package com.example.fanenqian.aidllive;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;
public class ServiceStateUtiles {
    static String TAG ="ServiceStateUtiles";
    public static Boolean isServiceRunning(Context context, String serviceName) {
        //获取服务方法  参数 必须用大写的Context！！！
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : infos) {
            String className = info.service.getClassName();
            Log.i(TAG,className);
            Log.i(TAG,"======"+info.process);
            if(serviceName.equals(className))
                return true;
        }
        return false;
    }

    public static Boolean isServiceProcessRunning(Context context, String serviceName) {
        //获取服务方法  参数 必须用大写的Context！！！
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : infos) {
            //String className = info.service.getClassName();
            Log.i(TAG,"======"+info.process);
            if(serviceName.equals(info.process))
                return true;
        }
        return false;
    }
}
