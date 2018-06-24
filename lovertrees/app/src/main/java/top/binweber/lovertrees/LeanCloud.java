package top.binweber.lovertrees;

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
        AVOSCloud.initialize(this,"hM0VRHvhx1XaPUI81Ci8v08V-gzGzoHsz","nm0JfuJmsJFenpnOdgp7BHk9");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);
    }
}
