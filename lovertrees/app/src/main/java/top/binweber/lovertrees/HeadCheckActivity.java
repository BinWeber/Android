package top.binweber.lovertrees;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

/**
 * Created by BinWe on 2018/3/16.
 */

public class HeadCheckActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_popup);

        TextView head_ensure = (TextView) findViewById(R.id.ensure);
        TextView head_cancel = (TextView) findViewById(R.id.cancel);

        ImageView head_1 = (ImageView) findViewById(R.id.head_1);
        ImageView head_2 = (ImageView) findViewById(R.id.head_2);
        ImageView head_3 = (ImageView) findViewById(R.id.head_3);
        ImageView head_4 = (ImageView) findViewById(R.id.head_4);
        ImageView head_5 = (ImageView) findViewById(R.id.head_5);
        ImageView head_6 = (ImageView) findViewById(R.id.head_6);

        final RadioButton button_1 = (RadioButton) findViewById(R.id.head_1_check);
        final RadioButton button_2 = (RadioButton) findViewById(R.id.head_2_check);
        final RadioButton button_3 = (RadioButton) findViewById(R.id.head_3_check);
        final RadioButton button_4 = (RadioButton) findViewById(R.id.head_4_check);
        final RadioButton button_5 = (RadioButton) findViewById(R.id.head_5_check);
        final RadioButton button_6 = (RadioButton) findViewById(R.id.head_6_check);

        head_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_1.setChecked(true);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        head_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_2.setChecked(true);
                button_1.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        head_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_3.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        head_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_4.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        head_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_5.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_6.setChecked(false);
            }
        });

        head_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_6.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
            }
        });

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_1.setChecked(true);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_2.setChecked(true);
                button_1.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_3.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_4.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_5.setChecked(false);
                button_6.setChecked(false);
            }
        });

        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_5.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_6.setChecked(false);
            }
        });

        button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_6.setChecked(true);
                button_1.setChecked(false);
                button_2.setChecked(false);
                button_3.setChecked(false);
                button_4.setChecked(false);
                button_5.setChecked(false);
            }
        });

        head_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_1.isChecked()){
                    AVUser.getCurrentUser().put("head", 1);
                    AVUser.getCurrentUser().saveInBackground();
                    UserPageActivity.head_image.setImageResource(R.drawable.header_1);
                    finish();
                } else if (button_2.isChecked()) {
                    AVUser.getCurrentUser().put("head", 2);
                    AVUser.getCurrentUser().saveInBackground();
                    UserPageActivity.head_image.setImageResource(R.drawable.header_2);
                    finish();
                } else if (button_3.isChecked()) {
                    AVUser.getCurrentUser().put("head", 3);
                    AVUser.getCurrentUser().saveInBackground();
                    UserPageActivity.head_image.setImageResource(R.drawable.header_3);
                    finish();
                } else if (button_4.isChecked()) {
                    AVUser.getCurrentUser().put("head", 4);
                    AVUser.getCurrentUser().saveInBackground();
                    UserPageActivity.head_image.setImageResource(R.drawable.header_4);
                    finish();
                } else if (button_5.isChecked()) {
                    AVUser.getCurrentUser().put("head", 5);
                    AVUser.getCurrentUser().saveInBackground();
                    UserPageActivity.head_image.setImageResource(R.drawable.header_5);
                    finish();
                } else if (button_6.isChecked()) {
                    AVUser.getCurrentUser().put("head", 6);
                    AVUser.getCurrentUser().saveInBackground();
                    UserPageActivity.head_image.setImageResource(R.drawable.header_6);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"请选择头像！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        head_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
