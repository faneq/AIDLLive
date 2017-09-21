package com.example.fanenqian.aidllive;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class HostService extends Service {
    private String mData = "服务器默认数据";
    private MyBinder binder;
    private String TAG = "AIDL_Service";
    private MyConnx conn;
    private boolean isRunning = false;

    public HostService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setData(String data) throws RemoteException {
            HostService.this.mData = data;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        flags = START_STICKY;

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        if (conn == null) {
            conn = new MyConnx();
        }
        keepAlive();
        Log.i(TAG, "onCreate服务启动");
        Toast.makeText(getApplicationContext(), "HostService服务开启", Toast.LENGTH_SHORT).show();
        printInfoThread();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy服务销毁");
        Toast.makeText(getApplicationContext(), "HostService服务停止", Toast.LENGTH_SHORT).show();
        isRunning = false;
        unbindService(conn);
        stopForeground(true);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        HostService.this.bindService(new Intent(HostService.this, LocalService.class), conn, Context.BIND_IMPORTANT);//重要级别
    }

    private class MyConnx implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "链接本地服务Service成功");
            Toast.makeText(getApplicationContext(), "链接本地服务Service成功", Toast.LENGTH_SHORT).show();
        }

        //正常unbind时不会调用这个回掉，而是在连接出现异常时（如被杀死）会发生这个回调。
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            String warnInfo = "本地服务LocalService被杀死";
            Log.i(TAG, warnInfo);
            Toast.makeText(HostService.this, warnInfo, Toast.LENGTH_SHORT).show();
            //再启动远程服务,并做好绑定bind
            //再启动远程服务,并做好绑定bind
            HostService.this.startService(new Intent(HostService.this, LocalService.class));
            HostService.this.bindService(new Intent(HostService.this, LocalService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    private void keepAlive() {
        try {
            Notification notification = new Notification();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notification.flags |= Notification.FLAG_ONGOING_EVENT;


            startForeground(0, notification); // 设置为前台服务避免kill，Android4.3及以上需要设置id为0时通知栏才不显示该通知；
            //startForeground(GRAY_SERVICE_ID, new Notification()); //显示通知 ，显示触摸即可了解详情或停止应用

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void printInfoThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                isRunning = true;
                while (isRunning) {
                    //System.out.print(mData);
                    Log.i(TAG, "服务运行中。。。打印：" + mData);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

}
