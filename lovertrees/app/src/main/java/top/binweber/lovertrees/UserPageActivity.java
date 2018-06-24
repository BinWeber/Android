package top.binweber.lovertrees;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

/**
 * Created by BinWe on 2018/3/15.
 */

public class UserPageActivity extends AppCompatActivity{

    public static ImageView head_image;

    public static String order = "order";
    public static String fellow = "fellow";
    public static String score = "score";
    public static String history = "history";
    public static String setting = "setting";
    public static String theme = "theme";
    public static String help = "help";

    private View my_order;
    private View my_fellow;
    private View my_score;
    private View my_history;
    private View my_setting;
    private View my_theme;
    private View my_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        my_order = findViewById(R.id.my_order);
        my_fellow = findViewById(R.id.my_fellow);
        my_score = findViewById(R.id.my_score);
        my_history = findViewById(R.id.my_history);
        my_setting = findViewById(R.id.my_setting);
        my_theme = findViewById(R.id.my_theme);
        my_help = findViewById(R.id.my_help);

        UserViewClick();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_page);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        head_image = (ImageView) findViewById(R.id.user_page_head);
        final TextView username = (TextView) findViewById(R.id.user_page_name);
        final Button logout_button = (Button) findViewById(R.id.user_page_logout);

        head_image.setImageResource(getResources().getIdentifier("header_" + AVUser.getCurrentUser().getInt("head"), "drawable", this.getPackageName()));
        username.setText(AVUser.getCurrentUser().getUsername());

        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPageActivity.this, HeadCheckActivity.class));
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder ensureBuilder = new AlertDialog.Builder(UserPageActivity.this);
                ensureBuilder.setTitle(R.string.tip).setIcon(R.mipmap.ic_launcher_round);
                final AlertDialog dialogShow = ensureBuilder.show();
                ensureBuilder.setMessage(R.string.ensure_log_out).setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AVUser.getCurrentUser().logOut();
                        MainActivity.MainActivity.finish();
                        finish();
                        startActivity(new Intent(UserPageActivity.this, MainActivity.class));
                        dialogShow.dismiss();
                    }
                });
                ensureBuilder.setMessage(R.string.ensure_log_out).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogShow.dismiss();
                    }
                });
                ensureBuilder.create().show();
            }
        });
    }

    private void UserViewClick(){
        final Intent intent = new Intent(this, UserPagerActivity.class);

        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("user_data", order);
                startActivity(intent);
            }
        });

        my_fellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("user_data", fellow);
                startActivity(intent);
            }
        });

        my_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("user_data", score);
                startActivity(intent);
            }
        });

        my_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("user_data", history);
                startActivity(intent);
            }
        });

        my_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("user_data", setting);
                startActivity(intent);
            }
        });

        my_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        my_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("user_data", help);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        navigateUpFromSameTask(this);
    }
}
