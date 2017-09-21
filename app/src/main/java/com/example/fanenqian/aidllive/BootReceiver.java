package com.example.fanenqian.aidllive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 该类继续自 BroadcastReceiver，覆载方法 onReceive 中，检测接收到的 Intent 是否符合
 * BOOT_COMPLETED，如果符合，则启动BootStartDemo这个Activity
 */
public class BootReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    //    android.intent.action.BOOT_COMPLETED
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BootBroadcastReceiver", "开机广播" + intent.getAction());
        if (intent.getAction().equals(ACTION)) {
            context.startService(new Intent(context, HostService.class));
            context.startService(new Intent(context, LocalService.class));
            Intent mainActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
    }

}
