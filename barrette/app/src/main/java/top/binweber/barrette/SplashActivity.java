package top.binweber.barrette;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.avos.avoscloud.AVUser;

/**
 * Created by wangb on 2017/9/16.
 */

public class SplashActivity extends AppCompatActivity{

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        final SharedPreferences setting = getSharedPreferences("SplashActivity",0);
        Boolean firstTime = setting.getBoolean("FIRST",true);

        if (!firstTime || AVUser.getCurrentUser() != null) {
            setContentView(R.layout.splash);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

        }else if(firstTime && AVUser.getCurrentUser() == null) {
            setContentView(R.layout.splash_one);

            Button mAccountButton = (Button) findViewById(R.id.account);
            mAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.edit().putBoolean("FIRST",false).commit();
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            });

            Button mAccountLaterButton = (Button) findViewById(R.id.account_later);
            mAccountLaterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.edit().putBoolean("FIRST",false).commit();
                    SplashActivity.this.finish();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            });

        }
    }

}
