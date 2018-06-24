package top.binweber.lovertrees;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import top.binweber.lovertrees.Fragment.ShopFragment;


/**
 * Created by BinWe on 2018/3/25.
 */

public class GoodsActivity extends AppCompatActivity{

    private ImageView goods_image;
    private TextView goods_name;
    private TextView goods_new;
    private TextView goods_email;

    private TextView goods_price;
    private TextView goods_price_boolean;
    private Button goods_button;

    private TextView goods_information;
    private TextView goods_message;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_goods);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        goods_image = (ImageView) findViewById(R.id.goods_image);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_new = (TextView) findViewById(R.id.goods_new);
        goods_email = (TextView) findViewById(R.id.goods_email);
        goods_price = (TextView) findViewById(R.id.goods_price);
        goods_price_boolean = (TextView) findViewById(R.id.goods_price_boolean);
        goods_button = (Button) findViewById(R.id.goods_button);
        goods_information = (TextView) findViewById(R.id.goods_information_text);
        goods_message = (TextView) findViewById(R.id.goods_information_message);

        Intent intent = getIntent();
        String data = intent.getStringExtra("goods_data");
        if (ShopFragment.goods_1.equals(data)) {
            goods_image.setImageResource(R.drawable.store_3);
            goods_name.setText("创意玻璃桌");
            goods_new.setText("九成新");
            goods_email.setText("焦作同城");
            goods_price.setText("￥19.9");
            goods_price_boolean.setText("");
            goods_information.setText("全部捐给焦作市梦琳儿童家园");
            goods_message.setText("特别好的小桌子，但是最近刚刚买了新的，这个没有地方放，所以转了。需要的联系哦！(平台担保)");
        } else if (ShopFragment.goods_2.equals(data)) {
            goods_image.setImageResource(R.drawable.store_4);
            goods_name.setText("骓驰入门级骑行自行车");
            goods_new.setText("八成新");
            goods_email.setText("焦作同城");
            goods_price.setText("￥650");
            goods_price_boolean.setVisibility(View.VISIBLE);
            goods_information.setText("无捐助对象");
            goods_message.setText("买了一年半，没怎么骑，转了。原价2200买的，同城可以来看车的。(平台担保)");
        } else if (ShopFragment.goods_3.equals(data)) {
            goods_image.setImageResource(R.drawable.store_5);
            goods_name.setText("陪你度过漫长岁月");
            goods_new.setText("");
            goods_email.setText("");
            goods_price.setText("￥53");
            goods_price_boolean.setText("");
            goods_information.setText("30%定向捐助给中国扶贫基金会");
            goods_message.setText("(创作故事）");
        } else if (ShopFragment.goods_4.equals(data)) {
            goods_image.setImageResource(R.drawable.store_6);
            goods_name.setText("追寻");
            goods_new.setText("");
            goods_email.setText("");
            goods_price.setText("￥79");
            goods_price_boolean.setText("");
            goods_information.setText("50%定向捐助给福建省宁德市扶贫开发协会");
            goods_message.setText("(创作故事）");
        } else if (ShopFragment.goods_5.equals(data)) {

        } else if (ShopFragment.goods_6.equals(data)) {

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
