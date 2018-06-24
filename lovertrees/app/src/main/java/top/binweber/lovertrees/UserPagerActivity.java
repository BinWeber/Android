package top.binweber.lovertrees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import top.binweber.lovertrees.Fragment.ShopFragment;

/**
 * Created by BinWe on 2018/3/27.
 */

public class UserPagerActivity extends AppCompatActivity {

    private View score;
    private View setting;
    private View order;
    private View about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_pager);

        score = findViewById(R.id.score_view);
        order = findViewById(R.id.order_view);
        setting = findViewById(R.id.setting_view);
        about = findViewById(R.id.about_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        Intent intent = getIntent();
        String data = intent.getStringExtra("user_data");
        if (UserPageActivity.order.equals(data)) {
            toolbar.setTitle(R.string.my_order);
            score.setVisibility(View.GONE);
            order.setVisibility(View.VISIBLE);
            setting.setVisibility(View.GONE);
            about.setVisibility(View.GONE);
        } else if(UserPageActivity.fellow.equals(data)) {
            toolbar.setTitle(R.string.my_fellow);
            score.setVisibility(View.GONE);
            order.setVisibility(View.VISIBLE);
            setting.setVisibility(View.GONE);
            about.setVisibility(View.GONE);
        } else if( UserPageActivity.history.equals(data)) {
            toolbar.setTitle(R.string.my_history);
            score.setVisibility(View.GONE);
            order.setVisibility(View.VISIBLE);
            setting.setVisibility(View.GONE);
            about.setVisibility(View.GONE);
        } else if(UserPageActivity.score.equals(data)) {
            toolbar.setTitle(R.string.my_score);
            score.setVisibility(View.VISIBLE);
            order.setVisibility(View.GONE);
            setting.setVisibility(View.GONE);
            about.setVisibility(View.GONE);
        } else if(UserPageActivity.setting.equals(data)) {
            toolbar.setTitle(R.string.my_setting);
            score.setVisibility(View.GONE);
            order.setVisibility(View.GONE);
            setting.setVisibility(View.VISIBLE);
            about.setVisibility(View.GONE);
        } else if(UserPageActivity.theme.equals(data)) {
            toolbar.setTitle(R.string.my_theme);
            score.setVisibility(View.GONE);
            order.setVisibility(View.GONE);
            setting.setVisibility(View.GONE);
            about.setVisibility(View.GONE);
        } else if(UserPageActivity.help.equals(data)) {
            toolbar.setTitle(R.string.my_help);
            score.setVisibility(View.GONE);
            order.setVisibility(View.GONE);
            setting.setVisibility(View.GONE);
            about.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
