package com.example.couscousapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.couscousapp.adapter.HomeAdapter;
import com.example.couscousapp.R;
import com.example.couscousapp.api.ApiRepository;
import com.example.couscousapp.api.JsonPlaceHolderApi;
import com.example.couscousapp.fragments.ContentBest;
import com.example.couscousapp.fragments.ContentNew;
import com.example.couscousapp.json_model.Data;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String userReference;
    public static Long personId;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle aBarDrawer;
    private NavigationView navView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    int fragmentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_f);

        // Set UserReference to android_id
        // Lucas Emulator 877e72c2434bc673
        userReference = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if(userReference.length() > 16){
            Log.w("warn","User reference might fail to be written to db if longer then 16 chars");
        }

        Log.i("Info", "android id: " + userReference);
        // Send UserReference to Server, if new, creates account in db
        final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
        apiRepository.apiCallUserReference(userReference);

        Toolbar tbLandingPage = findViewById(R.id.tb_landingpage);
        setSupportActionBar(tbLandingPage);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (fragmentFlag==0) {getSupportActionBar().setTitle("Bestbewertet");}
            else {getSupportActionBar().setTitle("Neu");}

        // Nav drawer
        drawer = findViewById(R.id.drawer_layout);
        aBarDrawer = new ActionBarDrawerToggle(this, drawer,R.string.Open, R.string.Close);
        drawer.addDrawerListener(aBarDrawer);
        aBarDrawer.syncState();
        navView = findViewById(R.id.navigation);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.login_drawer:
                        Toast.makeText(MainActivity.this, "Einloggen",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings_drawer:
                        Toast.makeText(MainActivity.this, "Einstellungen",Toast.LENGTH_SHORT).show();break;
                    case R.id.donate_drawer:
                        Toast.makeText(MainActivity.this, "Spenden", Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });

        // Fragment management
        ContentBest fragmentBest = new ContentBest();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, fragmentBest);
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
            case R.id.sort:
                // User chose the "Sort" item, show the app settings UI...
                Log.i("Info", "Button 'Sort' pressed");
                return true;

            case R.id.filter:
                // User chose the "Filter" item, show the app settings UI...
                Log.i("Info", "Button 'Filter' pressed");
                return true;

            case R.id.temp:
                // Switching between Fragments ContentBest & ContentNew
                Log.i("Info", "Button 'Temp' pressed");
                if(fragmentFlag==0) {ContentNew fragmentNew = new ContentNew();
                    fragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragmentNew).commit();
                    fragmentFlag = 1;
                    getSupportActionBar().setTitle("Neu");
                    Toast.makeText(this, "Sortierung: Neueste Artikel", Toast.LENGTH_LONG).show();}
                else {ContentBest fragmentBest = new ContentBest();
                    fragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragmentBest).commit();
                    fragmentFlag = 0;
                    getSupportActionBar().setTitle("Bestbewertet");
                    Toast.makeText(this, "Sortierung: Bestbewertete Artikel", Toast.LENGTH_LONG).show();}
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
