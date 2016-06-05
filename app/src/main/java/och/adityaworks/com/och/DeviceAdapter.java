package och.adityaworks.com.och;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aditya Pandey on 05-06-2016.
 */
public class DeviceAdapter extends ArrayAdapter<Device> {

    public DeviceAdapter(Context context, int resource, ArrayList<Device> lectures) {
        super(context, resource, lectures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Device device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_single, parent, false);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        icon.setImageResource(getIcon(device.type));
        name.setText(device.name);

        return convertView;

    }


    public int getIcon(String type) {
        int icon = 0;
        switch (type) {
            case "add" :
                icon = R.drawable.ic_add_devices;
                break;
            case "fan" :
                icon = R.drawable.ic_toys_black_24dp;
                break;
            case "bulb" :
                icon = R.drawable.ic_lightbulb;
                break;
            case "fridge" :
                icon = R.drawable.ic_kitchen_black_24dp;
                break;
            case "tv" :
                icon = R.drawable.ic_tv;
                break;
            case "door-lock" :
                icon = R.drawable.ic_lock_outline_black_24dp;
        }
        return icon;
    }
}
