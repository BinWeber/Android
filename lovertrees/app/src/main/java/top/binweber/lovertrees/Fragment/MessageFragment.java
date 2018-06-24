package top.binweber.lovertrees.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.binweber.lovertrees.R;

/**
 * Created by BinWe on 2018/3/27.
 */

public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_message, container, false);
        return view;
    }
}
