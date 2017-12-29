package top.binweber.lampsay;

import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangb on 2017/12/10.
 */

public class StateFragment extends Fragment {

    public static final String TAG = "StateFragment";

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int REQUEST_CONNECT_DEVICE = 2;
    public static final int REQUEST_CHAT = 3;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice romoteDevice;
    private BluetoothA2dp a2dp;

    public static String DeviceName;
    private static String DeviceAddress;

    private ArrayAdapter<Contacts> mContactsAdapter;
    private List<Contacts> mContactsObjects;
    private ListView mContextsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_main);
        toolbar.inflateMenu(R.menu.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_bluetooth:
                        Intent bluetoothConnectIntent = new Intent(getActivity(), BluetoothConnectActivity.class);
                        startActivityForResult(bluetoothConnectIntent, REQUEST_CONNECT_DEVICE);
                        break;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mContextsList = (ListView) getView().findViewById(R.id.contacts_list);
        mContextsList.setOnItemClickListener(mContactsClickListener);
        mContactsObjects = new ArrayList<Contacts>();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getActivity(), R.string.bt_enabled_leaving, Toast.LENGTH_SHORT).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data);
                }
        }
    }

    private void connectDevice(Intent data) {
        DeviceName = data.getExtras().getString(BluetoothConnectActivity.EXTRA_DEVICE_NAME);
        DeviceAddress = data.getExtras().getString(BluetoothConnectActivity.EXTRA_DEVICE_ADDRESS);
        romoteDevice = mBluetoothAdapter.getRemoteDevice(DeviceAddress);

        if(romoteDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
            BlueUtils mBluetoothUtils = new BlueUtils(romoteDevice);
            mBluetoothUtils.doPair();
            mBluetoothAdapter.getProfileProxy(getActivity(), mProfileServiceListener,BluetoothProfile.A2DP);
        } else {
            mBluetoothAdapter.getProfileProxy(getActivity(), mProfileServiceListener,BluetoothProfile.A2DP);
        }
    }

    private BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            try {
                if (profile == BluetoothProfile.A2DP) {
                    a2dp = (BluetoothA2dp) proxy;
                    if (a2dp.getConnectionState(romoteDevice) != BluetoothProfile.STATE_CONNECTED) {
                        a2dp.getClass().getMethod("connect", BluetoothDevice.class).invoke(a2dp, romoteDevice);
                        Toast.makeText(getActivity(), R.string.connect_successful, Toast.LENGTH_SHORT).show();
                        Contacts devices = new Contacts(DeviceName, DeviceAddress, R.drawable.icon, "今天");
                        mContactsObjects.add(devices);
                        mContactsAdapter = new ContactsAdapter(getActivity(), R.layout.contacts_list_name, mContactsObjects);
                        mContextsList.setAdapter(mContactsAdapter);
                    }
                } else if (profile == BluetoothProfile.HEADSET){

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(int profile) {

        }
    };

    private AdapterView.OnItemClickListener mContactsClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
            startActivityForResult(chatIntent, REQUEST_CHAT);
        }
    };

}
