package top.binweber.lampsay;

import android.Manifest;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;

import static top.binweber.lampsay.MainActivity.LOCATION_CODE;
import static top.binweber.lampsay.MainActivity.READ_CODE;
import static top.binweber.lampsay.MainActivity.RECORD_CODE;
import static top.binweber.lampsay.MainActivity.WRITE_CODE;

@PermissionsRequestSync(permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
        .RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, value = {LOCATION_CODE, RECORD_CODE, READ_CODE, WRITE_CODE})

public class MainActivity extends AppCompatActivity {

    private MenuItem menuItem;
    private BottomNavigationView mBottomNavigationView;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    private BluetoothAdapter mBluetoothAdapter;

    public static final int LOCATION_CODE = 0;
    public static final int RECORD_CODE = 1;
    public static final int READ_CODE = 2;
    public static final int WRITE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, StateFragment.REQUEST_ENABLE_BT);
        }

        Permissions4M.get(MainActivity.this).requestSync();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new StateFragment());
        mViewPagerAdapter.addFragment(new ProfileFragment());
        mViewPager.setAdapter(mViewPagerAdapter);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.navigation_states:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_profile:
                                mViewPager.setCurrentItem(1);
                        }
                        return false;
                    }
                }
        );

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = mBottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        });

        /*mViewPager.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/ //block slip
    }

    @PermissionsGranted(value = {LOCATION_CODE, RECORD_CODE, READ_CODE, WRITE_CODE})
    public void syncGranted(int code) {

    }

    @PermissionsDenied(value = {LOCATION_CODE, RECORD_CODE, READ_CODE, WRITE_CODE})
    public void syncDenied(int code) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(MainActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
