package top.binweber.barrette;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRationale;
import com.joker.api.Permissions4M;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private StringBuffer sbValues;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_DEVICE = 2;

    private TextView temp_text;
    private TextView qua_text;

    private CardView temp_View;
    private CardView qua_View;

    public static final int LOCATION_CODE = 9;

    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice romoteDevice = null;
    private BluetoothService mBluetoothService;


    private boolean connect_status_bit = false;
    private String mDeviceAddress;

    private double temp;
    private double temp_ave;
    private int qua;

    private LineChartView mLineChartView;
    private List<LineChartView.ItemBean> mItems;
    private int[] shadeColors;

    private TempControlView tempControl;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_child:
                    if(connect_status_bit){
                        temp_View.setVisibility(View.VISIBLE);
                        if(36.0 >= temp){
                            temp_text.setText(R.string.child_tmp_1);
                        }else if(36.2 < temp && temp <= 37.5){
                            temp_text.setText(R.string.child_tmp_2);
                        }else if(37.8 < temp && temp <= 38.5){
                            temp_text.setText(R.string.child_tmp_3 + "\n" + R.string.child_tmp_4);
                        }else if(temp > 38.5){
                            temp_text.setText(R.string.child_tmp_5);
                        }

                        qua_View.setVisibility(View.VISIBLE);
                        if(qua <= 200){
                            qua_text.setText(R.string.child_qua_1);
                        }else if(qua > 200 && qua <= 400){
                            qua_text.setText(R.string.child_qua_2);
                        }else if(qua > 400 && qua <= 600){
                            qua_text.setText(R.string.child_qua_3);
                        }else if(qua > 600 && qua <= 800){
                            qua_text.setText(R.string.child_qua_4);
                        }else if(qua > 800 && qua <= 1000){
                            qua_text.setText(R.string.child_qua_5);
                        }
                    }
                    return true;
                case R.id.navigation_adult:
                    if(connect_status_bit){
                        temp_View.setVisibility(View.VISIBLE);
                        if(36.3 <= temp && temp <= 37.3){
                            temp_text.setText(R.string.adult_tmp_1);
                        }else if(37.3 < temp && temp <= 38.5){
                            temp_text.setText(R.string.adult_tmp_2);
                        }else if(temp > 38.5){
                            temp_text.setText(R.string.adult_tmp_3);
                        }

                        qua_View.setVisibility(View.VISIBLE);
                        if(qua <= 200){
                            qua_text.setText(R.string.adult_qua_1);
                        }else if(qua > 200 && qua <= 400){
                            qua_text.setText(R.string.adult_qua_2);
                        }else if(qua > 400 && qua <= 600){
                            qua_text.setText(R.string.adult_qua_3);
                        }else if(qua > 600 && qua <= 800){
                            qua_text.setText(R.string.adult_qua_4);
                        }else if(qua > 800 && qua <= 1000){
                            qua_text.setText(R.string.adult_qua_5);
                        }
                    }
                    return true;
                case R.id.navigation_older:
                    if(connect_status_bit){
                        temp_View.setVisibility(View.VISIBLE);
                        temp_text.setTextSize(24);
                        if(35.0 >= temp ){
                            temp_text.setText(R.string.older_tmp_1);
                        }else if(36.0 < temp && temp <= 37.2){
                            temp_text.setText(R.string.older_tmp_2);
                        }else if(temp > 37.3){
                            temp_text.setText(R.string.older_tmp_3 + "\n" + R.string.older_tmp_4);
                        }

                        qua_View.setVisibility(View.VISIBLE);
                        if(qua <= 200){
                            qua_text.setText(R.string.older_qua_1);
                        }else if(qua > 200 && qua <= 400){
                            qua_text.setText(R.string.older_qua_2);
                        }else if(qua > 400 && qua <= 600){
                            qua_text.setText(R.string.older_qua_3);
                        }else if(qua > 600 && qua <= 800){
                            qua_text.setText(R.string.older_qua_4);
                        }else if(qua > 800 && qua <= 1000){
                            qua_text.setText(R.string.older_qua_5);
                        }
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChartView = (LineChartView) findViewById(R.id.lcv);
        initData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        temp_View = (CardView) findViewById(R.id.temp_tip_card);
        qua_View = (CardView) findViewById(R.id.qua_tip_card);

        temp_text = (TextView) findViewById(R.id.temp_tip_text);
        qua_text = (TextView) findViewById(R.id.qua_tip_text);
        temp_View.setVisibility(View.INVISIBLE);
        qua_View.setVisibility(View.INVISIBLE);

        tempControl = (TempControlView) findViewById(R.id.temp_control);
        tempControl.setAngleRate(3);
        tempControl.setTemp(0, 21, 0.0);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        sbValues = new StringBuffer();

        Intent intentService = new Intent(this,BluetoothService.class);
        startService(intentService);

        Permissions4M.get(MainActivity.this)
                .requestForce(true)
                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                .requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .requestCodes(LOCATION_CODE)
                .request();
    }

    @PermissionsGranted(LOCATION_CODE)
    public void granted() {
        Toast.makeText(MainActivity.this, R.string.location_enabled, Toast.LENGTH_SHORT).show();
    }

    @PermissionsRationale(LOCATION_CODE)
    public void rationale() {
        Toast.makeText(MainActivity.this, R.string.location_rational, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int  requestCode,int resultCode,Intent data){
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(MainActivity.this, R.string.bt_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
                else if(resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(MainActivity.this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == Activity.RESULT_OK){
                    connectDevice(data);
                }
        }
    }

    private void connectDevice(Intent data){
        String address = data.getExtras().getString(BluetoothConnectActivity.EXTRA_DEVICE_ADDRESS);
        mDeviceAddress = address;
        romoteDevice = mBluetoothAdapter.getRemoteDevice(address);

        Log.d(TAG,"address:"+ address);
        Intent gattServiceIntent = new Intent(this, BluetoothService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothService = ((BluetoothService.LocalBinder) service).getService();
            if (!mBluetoothService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            mBluetoothService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothService = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_bluetooth:
                Log.d(TAG,"connect bluetooth");
                Intent bluetoothIntent = new Intent(MainActivity.this,BluetoothConnectActivity.class);
                startActivityForResult(bluetoothIntent,REQUEST_CONNECT_DEVICE);
                break;
            case R.id.toolbar_about:
                AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(MainActivity.this);
                aboutBuilder.setTitle(R.string.about_app).setIcon(R.drawable.ic_launcher);
                aboutBuilder.setMessage("设备名:"+ Build.DEVICE + "\n" + "SDK版本：" + Build.VERSION.SDK_INT + "\n" + "软件版本：1.0").setPositiveButton(R.string.cancel,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                    }
                });
                aboutBuilder.create().show();
                break;
        }
        return true;
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothService.ACTION_GATT_CONNECTED.equals(action)) {
                connect_status_bit = true;
            } else if (BluetoothService.ACTION_GATT_DISCONNECTED.equals(action)) {
                connect_status_bit = false;
            } else if (BluetoothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                displayGattServices(mBluetoothService.getSupportedGattServices());
            } else if (BluetoothService.ACTION_DATA_AVAILABLE.equals(action)) {
               displayData(intent.getByteArrayExtra(BluetoothService.EXTRA_DATA));
            }
        }
    };

    private void displayData(byte[] data) {
        temp_View.setVisibility(View.VISIBLE);
        qua_View.setVisibility(View.VISIBLE);
        if (data != null && data.length > 0) {
            String res = new String(data);
            String tempS[] = res.split(",");
            temp= (double)Integer.valueOf(tempS[0]).intValue()/10;
            temp_ave = (double) Integer.valueOf(tempS[1]).intValue()/10;
            int qua = Integer.valueOf(tempS[2]).intValue();
            tempControl.setTemp(19, 40, temp);
            tempControl.setTitle("平均温度："+ temp_ave + "℃");
            tempControl.setAir("空气质量:" + qua);
        }

    }


    private void displayGattServices(List<BluetoothGattService> gattServices) {
        Log.d(TAG,"displayGattServices");

        if (gattServices == null)
            return;

        if (gattServices.size() > 0 && mBluetoothService.get_connected_status(gattServices) >= 4) {
            if (connect_status_bit) {
                mBluetoothService.enable_JDY_BLE(true);
                try {
                    Thread.currentThread();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mBluetoothService.enable_JDY_BLE(true);
                Toast.makeText(MainActivity.this, R.string.connect_successful, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,R.string.connect_bad, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothService != null) {

            final boolean result = mBluetoothService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothService = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(MainActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initData() {
        mItems = new ArrayList<>();
        mItems.add(new LineChartView.ItemBean(1489507200, 37));
        mItems.add(new LineChartView.ItemBean(1489593600, 39));
        mItems.add(new LineChartView.ItemBean(1489680000, 26));
        mItems.add(new LineChartView.ItemBean(1489766400, 25));
        mItems.add(new LineChartView.ItemBean(1489852800, 26));
        mItems.add(new LineChartView.ItemBean(1489939200, 19));
        mItems.add(new LineChartView.ItemBean(1490025600, 23));
        mItems.add(new LineChartView.ItemBean(1490112000, 34));
        mItems.add(new LineChartView.ItemBean(1490198400, 39));
        mItems.add(new LineChartView.ItemBean(1490284800, 17));

        shadeColors= new int[]{
                Color.argb(100, 255, 86, 86), Color.argb(15, 255, 86, 86),
                Color.argb(0, 255, 86, 86)};

        //  设置折线数据
        mLineChartView.setItems(mItems);
        //  设置渐变颜色
        mLineChartView.setShadeColors(shadeColors);
        //  开启动画
        mLineChartView.startAnim(mLineChartView,2000);
    }
}
