package top.binweber.lovertrees;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by BinWe on 2018/3/19.
 */

public class ItemAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    private Context context;
    private ViewPager viewPager;
    private ImageView[] mImageView;
    private LinearLayout mIndicator;
    private ViewPagerAdapter mAdapter;
    List<String> url;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    public ItemAdapter(List<MultipleItem> data, Context context) {
        super(data);
        this.context = context;
        addItemType(MultipleItem.TYPE_0, R.layout.shop_banner);
        addItemType(MultipleItem.TYPE_1, R.layout.shop_divider);
        addItemType(MultipleItem.TYPE_2, R.layout.shop_special);
        addItemType(MultipleItem.TYPE_3, R.layout.shop_goods);
    }

    @Override
    protected  void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TYPE_0:
                initViewPager(helper, item);
                if (!handler.hasMessages(0)) {
                    handler.sendEmptyMessage(0);
                }
                break;
            case MultipleItem.TYPE_1:
                helper.setText(R.id.shop_divider_text, item.getTitle());
                helper.setImageResource(R.id.shop_divider_image, item.getImage());
                break;
            case MultipleItem.TYPE_2:
                helper.setBackgroundRes(R.id.shop_special, item.getBackground());
                helper.setText(R.id.shop_special_text, item.getTitle());
                helper.setImageResource(R.id.shop_special_image, item.getImage());
                break;
            case MultipleItem.TYPE_3:
                helper.setImageResource(R.id.shop_goods_img, item.getImage());
                helper.setText(R.id.shop_goods_name, item.getTitle());
                helper.setText(R.id.shop_goods_price, item.getPrice());
                helper.setText(R.id.shop_goods_type, item.getType());
                break;
        }
    }

    private void initViewPager(BaseViewHolder helper, MultipleItem item) {
        url = item.getList();
        mIndicator = (LinearLayout) helper.getView(R.id.shop_banner_indicator);
        viewPager = (ViewPager) helper.getView(R.id.shop_banner_viewPager);

        mAdapter = new ViewPagerAdapter(context, url);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(5000 * (url.size() + 1));

        if (mImageView == null) {
            initIndicator();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        mImageView = new ImageView[url.size()];
        for (int i = 0; i < url.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.indicator, null);
            view.findViewById(R.id.indicator_image).setBackgroundResource(R.mipmap.red_indicator);
            mImageView[i] = new ImageView(context);
            if (i == 0) {
                mImageView[i].setBackgroundResource(R.mipmap.red_indicator);
            } else {
                mImageView[i].setBackgroundResource(R.mipmap.gray_indicator);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 0, 0, 0);
                mImageView[i].setLayoutParams(layoutParams);
            }
            mIndicator.addView(mImageView[i]);
        }
    }

    private void setIndicator(int position) {
        position %= url.size();
        for (int i = 0; i < url.size(); i++) {
            mImageView[i].setBackgroundResource(R.mipmap.red_indicator);
            if (position != i) {
                mImageView[i].setBackgroundResource(R.mipmap.gray_indicator);
            }
        }
    }
}
