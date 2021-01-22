package com.example.couscousapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.couscousapp.R;
import com.example.couscousapp.api.ApiRepository;
import com.example.couscousapp.fragments.ContentDay;
import com.example.couscousapp.fragments.ContentMonth;
import com.example.couscousapp.fragments.ContentWeek;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static String userReference;
    public static Long personId;

    private ActionBarDrawerToggle aBarDrawer;
    private FragmentManager fragmentManager;
    int fragmentFlag;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_f);

        // Set UserReference to android_id
        // Lucas Emulator 877e72c2434bc673
        userReference = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if(userReference.length() > 16){
            Log.w("warn","User reference might fail to be written to db if longer than 16 chars");
        }

        Log.i("Info", "android id: " + userReference);
        // Send UserReference to Server, if new, creates account in db
        final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
        apiRepository.apiCallUserReference(userReference);

        Toolbar tbLandingPage = findViewById(R.id.tb_landingpage);
        setSupportActionBar(tbLandingPage);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_landingpage);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        ContentDay fragmentDay = new ContentDay();
                        fragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragmentDay).commit();
                        break;

                    case 1:
                        ContentWeek fragmentWeek = new ContentWeek();
                        fragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragmentWeek).commit();
                        //Toast.makeText(this, "Sortierung: Woche", Toast.LENGTH_LONG).show();
                        break;

                    case 2:
                        ContentMonth fragmentMonth = new ContentMonth();
                        fragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragmentMonth).commit();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // called when tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // called when a tab is reselected
            }
        });

        // Nav drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        aBarDrawer = new ActionBarDrawerToggle(this, drawer,R.string.open, R.string.close);
        drawer.addDrawerListener(aBarDrawer);
        aBarDrawer.syncState();
        NavigationView navView = findViewById(R.id.navigation);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.login_drawer:
                        Toast.makeText(MainActivity.this, "Einloggen (Soon™)",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings_drawer:
                        Toast.makeText(MainActivity.this, "Einstellungen (Soon™)",Toast.LENGTH_SHORT).show();break;
                    case R.id.download_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://comppress.de/wp-content/uploads/CouscousApp.apk")));break;
                    case R.id.homepage_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://comppress.de/")));break;
                    case R.id.join_drawer:
                        Toast.makeText(MainActivity.this, "Mitglied werden (Soon™)",Toast.LENGTH_SHORT).show();break;
                    case R.id.donate_drawer:
                        Toast.makeText(MainActivity.this, "Spenden (Soon™)", Toast.LENGTH_SHORT).show();break;
                    case R.id.github_drawer:
                        Toast.makeText(MainActivity.this, "Github (Soon™)",Toast.LENGTH_SHORT).show();break;
                    case R.id.supportchat_drawer:
                        Toast.makeText(MainActivity.this, "Support Chat (Soon™)", Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });

        // Fragment management
        final ContentDay fragmentDay = new ContentDay();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, fragmentDay);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tb_landingpage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:
                // User chose the "Search" item, show the app settings UI...
                Toast.makeText(this, "Suche (Soon™)", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.filter:
                // User chose the "Filter" item, show the app settings UI...
                Toast.makeText(this, "Inhalte filtern (Soon™)", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                if(aBarDrawer.onOptionsItemSelected(item))
                    return true;
                return super.onOptionsItemSelected(item);

        }
    }
}
