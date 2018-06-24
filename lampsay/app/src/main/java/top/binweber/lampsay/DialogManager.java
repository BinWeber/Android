package top.binweber.lampsay;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wangb on 2017/12/6.
 */

public class DialogManager {
    private Dialog mDialog;

    private ImageView mIcon;
    private ImageView mVoice;

    private TextView mLable;

    private Context mContext;

    public DialogManager(Context context){
        mContext = context;
    }

    public void showRecordingDialog(){
        mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.dialog_recorder,null);
        mDialog.setContentView(view);

        mIcon = view.findViewById(R.id.recorder_dialog_icon);
        mVoice = view.findViewById(R.id.recorder_dialog_voice);
        mLable = view.findViewById(R.id.recorder_dialog_text);

        mDialog.show();
    }

    //正在播放时的状态
    public void recording() {
        if (mDialog != null&&mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.recorder);
            mLable.setText(R.string.str_dialog_recording);
        }
    }
    //想要取消
    public void wantToCancel(){
        if (mDialog != null&&mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.cancel);
            mLable.setText(R.string.str_dialog_want_to_cancel);
        }
    }
    //录音时间太短
    public void tooShort() {
        if (mDialog != null&&mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.voice_to_short);
            mLable.setText(R.string.str_dialog_too_short);
        }
    }
    //关闭dialog
    public void dimissDialog(){
        if (mDialog != null&&mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 通过level更新voice上的图片
     *
     * @param level
     */
    public void updateVoiceLevel(int level){
        if (mDialog != null&&mDialog.isShowing()) {

            int resId = mContext.getResources().getIdentifier("v" + level, "drawable", mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}