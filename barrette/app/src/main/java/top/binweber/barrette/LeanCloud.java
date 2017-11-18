package top.binweber.barrette;

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
        AVOSCloud.initialize(this,"kMUCNnfB0nUeraVF9mFy7jLU-gzGzoHsz","CXIL2E08eMTibzQo1Bdxeq0V");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);
    }
}
