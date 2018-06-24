package top.binweber.lovertrees.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * Created by BinWe on 2018/3/27.
 */

public class OrganizationFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<MultipleItem> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_organization, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.organization_recycler);
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

                        break;
                    case 1:

                        break;
                    case 2:

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
                        break;
                    case 8:
                        break;
                    default:
                        break;
                }
            }

        });
    }

    private List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> data = new ArrayList<>();

        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.organization_1, "爱心联盟", "","", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.organization_2, "小脚丫儿童公益", "","", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.organization_3, "儿童少年基金会", "","", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.organization_4, "壹基金", "","", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.organization_5, "志愿者协会", "","", MultipleItem.TYPE_SPAN_SIZE_10));
        data.add(new MultipleItem(MultipleItem.TYPE_3, R.drawable.organization_6, "动物保护协会", "","", MultipleItem.TYPE_SPAN_SIZE_10));

        return data;
    }

}
