package top.binweber.lampsay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangb on 2017/12/27.
 */

public class ContactsAdapter extends ArrayAdapter<Contacts> {

    private Context context;

    private int resourceId;

    private List<Contacts> objects;

    public ContactsAdapter(Context context, int resource, List<Contacts> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ImageView headImage;
        TextView name;
        TextView address;
        TextView time;

        if(convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(context).inflate(resourceId, null);
        }

        headImage = (ImageView) view.findViewById(R.id.contacts_head);
        name = (TextView) view.findViewById(R.id.contacts_name);
        address = (TextView) view.findViewById(R.id.contacts_address);
        time = (TextView) view.findViewById(R.id.contacts_time);

        headImage.setImageResource(objects.get(position).getImageId());
        name.setText(objects.get(position).getName());
        address.setText(objects.get(position).getAddress());
        time.setText(objects.get(position).getTime());

        return view;
    }
}
