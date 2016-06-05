package och.adityaworks.com.och;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddDevice extends AppCompatActivity {

    private static final String LOG_TAG = AddDevice.class.getSimpleName();
    public static GridView gridView;
    public static DeviceAdapter deviceAdapter;
    public static String deviceStr;
    public static Context context;

    @Override
    public void onStart() {
        super.onStart();
        DeviceListUpdater updaterTask = new DeviceListUpdater(this);
        updaterTask.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        context = getApplicationContext();

        gridView = (GridView) findViewById(R.id.device_list);
        ArrayList<Device> devices = getDeviceList();
        deviceAdapter = new DeviceAdapter(
                this, R.layout.grid_single,
                devices
        );

        gridView.setAdapter(deviceAdapter);
    }

    public static ArrayList<Device> getDeviceList() {
        ArrayList<Device> devices;

        try {
            devices = getDeviceListFromJson(getDeviceStr());
            return devices;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Device> getDeviceListFromJson(String deviceStr)
            throws JSONException {
        final String DEVICE_NAME = "name";
        final String DEVICE_TYPE = "type";

        JSONArray deviceArray = new JSONArray(deviceStr);
        ArrayList<Device> resultList = new ArrayList<>();

        for (int i = 0; i < deviceArray.length(); i++) {
            JSONObject deviceObject = deviceArray.getJSONObject(i);
            String name = deviceObject.getString(DEVICE_NAME);
            String type = deviceObject.getString(DEVICE_TYPE);

            resultList.add(new Device(name, type));
        }

        return resultList;
    }

    public static String getDeviceStr() {
        String deviceList = "[{\"_id\":\"5753c81c76c503dc03000002\",\"name\":\"Add New\",\"type\":\"add\",\"__v\":0}]";
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("deviceStr", deviceList);
    }
}
