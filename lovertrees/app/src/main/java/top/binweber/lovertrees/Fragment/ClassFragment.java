package top.binweber.lovertrees.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.binweber.lovertrees.R;

/**
 * Created by BinWe on 2018/3/27.
 */

public class ClassFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_class, container, false);

        return view;
    }
}
