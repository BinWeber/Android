package top.binweber.barrette;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRationale;
import com.joker.api.Permissions4M;

import java.util.Set;

/**
 * Created by wangb on 2017/9/16.
 */

public class BluetoothConnectActivity extends Activity {

    private static final String TAG = "DeviceListActivity";

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;

    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    public static final int LOCATION_CODE = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_device_list);
        setResult(Activity.RESULT_CANCELED);
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mNewDevicesArrayAdapter.clear();
                doDiscovery();
            }
        });

        ArrayAdapter<String> pairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bluetooth_device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bluetooth_device_name);

        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, MainActivity.REQUEST_ENABLE_BT);
        }

        Permissions4M.get(BluetoothConnectActivity.this)
                .requestForce(true)
                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                .requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .requestCodes(LOCATION_CODE)
                .request();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for(BluetoothDevice device : pairedDevices){
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }else{
            String noDevices = getResources().getText(R.string.none_found).toString();
            pairedDevicesArrayAdapter.add(noDevices);
        }
    }

    @PermissionsGranted(LOCATION_CODE)
    public void granted() {
        Toast.makeText(BluetoothConnectActivity.this, R.string.location_enabled, Toast.LENGTH_SHORT).show();
    }

    @PermissionsRationale(LOCATION_CODE)
    public void rationale() {
        Toast.makeText(BluetoothConnectActivity.this, R.string.location_rational, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(mBtAdapter != null){
            mBtAdapter.startDiscovery();
        }

        this.unregisterReceiver(mReceiver);
    }

    private void doDiscovery(){
        setTitle(R.string.scanning);
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        if(mBtAdapter.isDiscovering()){
            mBtAdapter.cancelDiscovery();
        }

        mBtAdapter.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device.getBondState() != BluetoothDevice.BOND_BONDED){
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                setTitle(R.string.select_device);
                if(mNewDevicesArrayAdapter.getCount() == 0){
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> av,View v,int arg2,long arg3){
            mBtAdapter.cancelDiscovery();

            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS,address);

            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(BluetoothConnectActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
