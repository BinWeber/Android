package top.binweber.lampsay;

import android.app.Application;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by wangb on 2017/10/23.
 */

public class LeanCloud extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        AVOSCloud.initialize(this,"Ll8zyYs2opkRxdq4zBQ4bgmE-gzGzoHsz","IX9CLM12wSozJ80UewJgC8tT");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);
    }
}
