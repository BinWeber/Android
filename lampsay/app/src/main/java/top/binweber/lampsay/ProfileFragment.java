package top.binweber.lampsay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;

/**
 * Created by wangb on 2017/12/10.
 */

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        final ImageView nav_head = (ImageView) view.findViewById(R.id.head_icon);
        final TextView nav_username = (TextView)view.findViewById(R.id.username);
        final Button nav_login = (Button) view.findViewById(R.id.login_button);

        if (AVUser.getCurrentUser() != null) {
            nav_head.setImageResource(R.drawable.icon);
            nav_username.setText(AVUser.getCurrentUser().getUsername());
            nav_username.setVisibility(View.VISIBLE);
            nav_login.setText(R.string.log_out);
        } else {
            nav_head.setImageResource(R.mipmap.ic_launcher_round);
            nav_username.setVisibility(View.GONE);
            nav_login.setText(R.string.log_in);
        }

        nav_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (AVUser.getCurrentUser() != null) {
                    final AlertDialog.Builder ensureBuilder = new AlertDialog.Builder(getActivity());
                    ensureBuilder.setTitle(R.string.log_out).setIcon(R.mipmap.ic_launcher_round);
                    final AlertDialog dialogShow = ensureBuilder.show();
                    ensureBuilder.setMessage(R.string.ensure_log_out).setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AVUser.getCurrentUser().logOut();
                            nav_login.setText(R.string.login);
                            nav_head.setImageResource(R.mipmap.ic_launcher_round);
                            nav_username.setVisibility(View.GONE);
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
                } else if(AVUser.getCurrentUser() == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }
        });

        View AboutView = view.findViewById(R.id.about);
        AboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(getActivity());
                aboutBuilder.setTitle(R.string.about).setIcon(R.mipmap.ic_launcher);
                aboutBuilder.setMessage("设备名:"+ Build.DEVICE + "\n" + "SDK版本：" + Build.VERSION.SDK_INT + "\n" + "版本号：V1.3(180316)").setPositiveButton(R.string.button_cancel,new DialogInterface.OnClickListener(){
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
