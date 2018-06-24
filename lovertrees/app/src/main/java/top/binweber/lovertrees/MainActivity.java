package top.binweber.lovertrees;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

import top.binweber.lovertrees.Fragment.ClassFragment;
import top.binweber.lovertrees.Fragment.HomeFragment;
import top.binweber.lovertrees.Fragment.MessageFragment;
import top.binweber.lovertrees.Fragment.OrganizationFragment;
import top.binweber.lovertrees.Fragment.RecruitFragment;
import top.binweber.lovertrees.Fragment.ShopFragment;
import top.binweber.lovertrees.Fragment.SquareFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Activity MainActivity;

    private static FragmentManager mFragmentManager;

    private Fragment homeFragment;
    private Fragment shopFragment;
    private Fragment messageFragment;
    private Fragment organizationFragment;
    private Fragment squareFragment;
    private Fragment recruitFragment;
    private Fragment classFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity = this;

        homeFragment = new HomeFragment();
        shopFragment = new ShopFragment();
        messageFragment = new MessageFragment();
        organizationFragment = new OrganizationFragment();
        squareFragment = new SquareFragment();
        recruitFragment = new RecruitFragment();
        classFragment = new ClassFragment();

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fragment_content, homeFragment).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        final ImageView nav_header_image = (ImageView) headerLayout.findViewById(R.id.nav_header_image);
        final TextView nav_username = (TextView) headerLayout.findViewById(R.id.nav_username);
        final Button nav_login_button = (Button) headerLayout.findViewById(R.id.nav_login_button);

        if (AVUser.getCurrentUser() != null) {
            nav_header_image.setImageResource(getResources().getIdentifier("header_" + AVUser.getCurrentUser().getInt("head"), "drawable", this.getPackageName()));
            nav_username.setText(AVUser.getCurrentUser().getUsername());
            nav_username.setVisibility(View.VISIBLE);
            nav_login_button.setVisibility(View.GONE);
        } else {
            nav_header_image.setImageResource(R.drawable.header_0);
            nav_username.setVisibility(View.GONE);
            nav_login_button.setVisibility(View.VISIBLE);
        }

        nav_header_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AVUser.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, UserPageActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "请登录！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        nav_login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        item.setCheckable(true);
        item.setChecked(true);
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle(R.string.home);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, homeFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_shop) {
            setTitle(R.string.shop);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, shopFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_message) {
            setTitle(R.string.message);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, messageFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_recruit) {
            setTitle(R.string.recruit);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, recruitFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_organization) {
            setTitle(R.string.organization);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, organizationFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_square) {
            setTitle(R.string.square);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, squareFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
            /*final AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(MainActivity.this);
            aboutBuilder.setTitle(R.string.about).setIcon(R.mipmap.ic_launcher);
            aboutBuilder.setMessage("设备名:"+ Build.DEVICE + "\n" + "SDK版本：" + Build.VERSION.SDK_INT + "\n" + "版本号：Demo-V0.5(180315)").setPositiveButton(R.string.cancel,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog,int which){
                }
            });
            aboutBuilder.create().show();*/
        } else if (id == R.id.nav_course) {
            setTitle(R.string.course);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, classFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);
        }
        return true;
    }

}
