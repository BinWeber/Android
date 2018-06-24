package top.binweber.lovertrees;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by BinWe on 2018/3/26.
 */

public class BroadcastManager {

    private static BroadcastManager broadCastManager = new BroadcastManager();

    public static BroadcastManager getInstance() {
        return broadCastManager;
    }

    //注册广播接收者
    public void registerReceiver(Activity activity,
                                 BroadcastReceiver receiver, IntentFilter filter) {
        activity.registerReceiver(receiver, filter);
    }

    //注销广播接收者
    public void unregisterReceiver(Activity activity,
                                   BroadcastReceiver receiver) {
        activity.unregisterReceiver(receiver);
    }

    //发送广播
    public void sendBroadCast(Activity activity, Intent intent) {
        activity.sendBroadcast(intent);
    }

}

