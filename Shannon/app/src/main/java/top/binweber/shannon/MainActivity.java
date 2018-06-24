package top.binweber.shannon;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static FragmentManager mFragmentManager;
    private Fragment entropyFragment;
    private Fragment uniqueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.entropy);
        entropyFragment = new EntropyFragment();
        uniqueFragment = new UniqueFragment();

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fragment_content, entropyFragment).commit();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        item.setCheckable(true);
        item.setChecked(true);

        int id = item.getItemId();

        if (id == R.id.nav_entropy) {
            setTitle(R.string.entropy);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, entropyFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);

        } else if (id == R.id.nav_unique) {
            setTitle(R.string.unique);
            mFragmentManager.beginTransaction().setCustomAnimations( R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit).replace(R.id.fragment_content, uniqueFragment).commit();
            drawer.closeDrawer(Gravity.LEFT);

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            drawer.closeDrawer(Gravity.LEFT);
        }
        return true;
    }
}
