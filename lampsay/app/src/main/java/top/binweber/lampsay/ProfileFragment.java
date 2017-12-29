package top.binweber.lampsay;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangb on 2017/12/10.
 */

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        View SettingView = view.findViewById(R.id.setting);
        View AboutView = view.findViewById(R.id.about);
        AboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(getActivity());
                aboutBuilder.setTitle(R.string.about).setIcon(R.mipmap.ic_launcher);
                aboutBuilder.setMessage("设备名:"+ Build.DEVICE + "\n" + "SDK版本：" + Build.VERSION.SDK_INT + "\n" + "版本号：V1.0(171228)").setPositiveButton(R.string.button_cancel,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                    }
                });
                aboutBuilder.create().show();
            }
        });

        return view;

    }
}
