package org.comppress.android.activity;

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
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.comppress.android.R;
import org.comppress.android.api.ApiRepository;
import org.comppress.android.fragments.ContentDay;
import org.comppress.android.fragments.ContentMonth;
import org.comppress.android.fragments.ContentWeek;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static String userReference;
    public static Long personId;
    public static String language;

    private ActionBarDrawerToggle aBarDrawer;
    private FragmentManager fragmentManager;
    int fragmentFlag;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        language = Locale.getDefault().getLanguage();
        if (!language.equals("de")) {

            language = "en";
        }

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

        final TabLayout tabLayout = findViewById(R.id.tablayout_landingpage);
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

        Menu m = navView.getMenu();
        MenuItem menuItem = m.findItem(R.id.login_drawer);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan((getResources().getColor(R.color.colorComingSoon))), 0, s.length(), 0);
        menuItem.setTitle(s);

        menuItem = m.findItem(R.id.settings_drawer);
        s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan((getResources().getColor(R.color.colorComingSoon))), 0, s.length(), 0);
        menuItem.setTitle(s);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.login_drawer:
                        Toast.makeText(MainActivity.this, getString(R.string.login) + " " + getString(R.string.in_progress),Toast.LENGTH_SHORT).show();break;
                    case R.id.settings_drawer:
                        Toast.makeText(MainActivity.this, getString(R.string.settings) + " " + getString(R.string.in_progress),Toast.LENGTH_SHORT).show();break;
                    case R.id.language_drawer:
                        if (language.equals("de")) {
                            language = "en";
                        } else {
                            language = "de";
                        }
                        final ContentDay fragmentDay = new ContentDay();
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.fragment_placeholder, fragmentDay);
                        fragmentTransaction.commit();
                        break;
                    case R.id.homepage_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://comppress.org/")));break;
                    case R.id.join_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://comppress.org/support/#mitglied")));break;
                    case R.id.publisher_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://comppress.org/publisher-information/")));break;
                    case R.id.github_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/comppress")));break;
                    case R.id.supportchat_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/comppressorg")));break;
                    case R.id.license_drawer:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/comppress/comppress-app/blob/master/EXTERNAL_LICENSES")));break;
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
                Toast.makeText(this, getString(R.string.search) + " " + getString(R.string.in_progress), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.filter:
                // User chose the "Filter" item, show the app settings UI...
                Toast.makeText(this, getString(R.string.filter) + " " + getString(R.string.in_progress), Toast.LENGTH_SHORT).show();
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
