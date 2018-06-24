package top.binweber.lovertrees.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import top.binweber.lovertrees.GoodsActivity;
import top.binweber.lovertrees.ItemAdapter;
import top.binweber.lovertrees.MultipleItem;
import top.binweber.lovertrees.R;


/**
 * Created by BinWe on 2018/3/17.
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<MultipleItem> data;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler);
        Init();

        return view;
    }

    private void Init() {
        data = getMultipleItemData();
        final ItemAdapter itemAdapter = new ItemAdapter(data, getActivity());
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), MultipleItem.TYPE_SPAN_SIZE_20);
        recyclerView.setLayoutManager(manager);
        itemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        recyclerView.setAdapter(itemAdapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getActivity(), GoodsActivity.class);

                switch (position){
                    case 0:
                        viewPager = view.findViewById(R.id.shop_banner_viewPager);
                        viewPager.setOnTouchListener(new View.OnTouchListener() {
                            int flage = 0;
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        flage = 0;
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        flage = 1;
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        if (flage == 0) {
                                            int item = viewPager.getCurrentItem();
                                            if (item % 5 == 0) {

                                            } else if (item % 5 == 1) {

                                            } else if (item % 5 == 2) {

                                            } else if (item % 5 == 3) {

                                            } else if (item % 5 == 4) {

                                            }
                                        }
                                }
                                return false;
                            }
                        });
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:
                        break;
                    case 7:
                        intent.putExtra("goods_data", ShopFragment.goods_1);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.putExtra("goods_data", ShopFragment.goods_2);
                        startActivity(intent);
                        break;
                    case 9:
                        intent.putExtra("goods_data", ShopFragment.goods_3);
                        startActivity(intent);
                        break;
                    case 10:
                        intent.putExtra("goods_data", ShopFragment.goods_4);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

        });
    }

    private List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> data = new ArrayList<>();

        data.add(new MultipleItem(MultipleItem.TYPE_0, InitData(), MultipleItem.TYPE_SPAN_SIZE_20));
        data.add(new MultipleItem(MultipleItem.TYPE_1, R.drawable.ic_organization_red, "优秀公益组织", MultipleItem.TYPE_SPAN_SIZE_20));
        data.add(new MultipleItem(MultipleItem.TYPE_2, R.mipmap.shop_part_4, R.drawable.organization_1, "爱心联盟", MultipleItem.TYPE_SPAN_SIZE_5));
        data.add(new MultipleItem(MultipleItem.TYPE_2, R.mipmap.shop_part_2, R.drawable.organization_4, "壹基金", MultipleItem.TYPE_SPAN_SIZE_5));
        data.add(new MultipleItem(MultipleItem.TYPE_2, R.mipmap.shop_part_1, R.drawable.organization_6, "动保协会", MultipleItem.TYPE_SPAN_SIZE_5));
        data.add(new MultipleItem(MultipleItem.TYPE_2, R.mipmap.shop_part_3, R.drawable.organization_2, "小脚丫", MultipleItem.TYPE_SPAN_SIZE_5));
        data.add(new MultipleItem(MultipleItem.TYPE_1, R.drawable.ic_hot_red, "热卖商品", MultipleItem.TYPE_SPAN_SIZE_20));

        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.store_3, "创意玻璃桌", "￥19.9","二手", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.store_4, "骓驰入门级骑行自行车", "￥650","二手", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.store_5, "陪你度过漫长岁月", "￥53","饰品", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.store_6, "追寻", "￥79","居间用品", MultipleItem.TYPE_SPAN_SIZE_10));

        return data;
    }

    private List<String> InitData() {
        List<String> urls = new ArrayList<>();
        urls.add("https://t1.picb.cc/uploads/2018/03/28/2wkeu7.jpg");
        urls.add("https://t1.picb.cc/uploads/2018/03/28/2wkc6M.jpg");
        urls.add("https://t1.picb.cc/uploads/2018/03/28/2wkl36.jpg");
        urls.add("https://t1.picb.cc/uploads/2018/03/28/2wkIpe.jpg");
        urls.add("https://t1.picb.cc/uploads/2018/03/28/2wkiYs.jpg");
        return urls;
    }

}
