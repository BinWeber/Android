package top.binweber.lampsay;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangb on 2017/12/7.
 */

public class RecorderAdapter extends ArrayAdapter<ChatActivity.Recorder> {

    private int mMinItemWidth;
    private int mMaxItemWidth;

    private LayoutInflater mInflater;

    public RecorderAdapter(Context context, List<ChatActivity.Recorder> datas) {
        super(context, -1, datas);

        mInflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.recorder_message, parent, false);
            holder = new ViewHolder();
            holder.seconds = convertView.findViewById(R.id.recorder_time);
            holder.length = convertView.findViewById(R.id.recorder_length);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.seconds.setText(Math.round(getItem(position).time) + "\"");
        ViewGroup.LayoutParams lp = holder.length.getLayoutParams();
        lp.width =(int) (mMinItemWidth + (mMaxItemWidth / 60f * getItem(position).time));
        return convertView;
    }

    private class ViewHolder {
        TextView seconds;
        View length;
    }
}
