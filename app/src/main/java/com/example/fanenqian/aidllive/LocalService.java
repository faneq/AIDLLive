package com.example.fanenqian.aidllive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by fanenqian on 2017/9/21.
 */

public class LocalService extends Service {
    String TAG = "LocalService";
    private MyConn conn;
    private MyLocalBind binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    class MyLocalBind extends IMyAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setData(String data) throws RemoteException {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "LocalService服务停止", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "LocalService开启", Toast.LENGTH_SHORT).show();
        binder = new MyLocalBind();
        if (conn == null) {
            conn = new MyConn();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        LocalService.this.bindService(new Intent(LocalService.this, HostService.class), conn, Context.BIND_IMPORTANT);
    }

    //绑定service需要一个链接，我们实现ServiceConnection
    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "链接远程服务AIDLService成功");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            String warnInfo = "远程服务AIDLService被杀死";
            Log.i(TAG, warnInfo);
            Toast.makeText(LocalService.this, warnInfo, Toast.LENGTH_SHORT).show();
            //再启动远程服务,并做好绑定bind
            LocalService.this.startService(new Intent(LocalService.this, HostService.class));
            LocalService.this.bindService(new Intent(LocalService.this, HostService.class), conn, Context.BIND_IMPORTANT);
        }
    }
}
