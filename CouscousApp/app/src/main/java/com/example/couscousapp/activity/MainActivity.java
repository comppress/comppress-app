package com.example.couscousapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.couscousapp.adapter.HomeAdapter;
import com.example.couscousapp.R;
import com.example.couscousapp.api.JsonPlaceHolderApi;
import com.example.couscousapp.fragments.ContentBest;
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

    /*private List<Data> dataList;*/

    //android views
    /*private ProgressBar progressBar;
    private RecyclerView recyclerView;*//*
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;*/
    private DrawerLayout drawer;
    private ActionBarDrawerToggle aBarDrawer;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_f);

        Toolbar tbLandingPage = (Toolbar) findViewById(R.id.tb_landingpage);
        setSupportActionBar(tbLandingPage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //nav drawer
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

        /*dataList = new ArrayList<>();*/

        /*adapter = new HomeAdapter(dataList, this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);*/

        /*recyclerView = findViewById(R.id.rv_landingpage);
        progressBar = findViewById(R.id.pb_landingpage);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//Todo        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        apiCallData(jsonPlaceHolderApi);*/

        //fragment management
        ContentBest fragmentBest = new ContentBest();
        /*ContentNew fragmentNew = new ContentNew();*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                if(aBarDrawer.onOptionsItemSelected(item))
                    return true;
                return super.onOptionsItemSelected(item);

        }
    }

    /*public void apiCallData(JsonPlaceHolderApi jsonPlaceHolderApi){

        Call<List<Data>> call = jsonPlaceHolderApi.getRatedData();
        // Cant run this on the UI Thread, Retrofit runs it for us on a background thread
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG,"apiCallData return Code != 200");
                    return;
                }

                progressBar.setVisibility(View.GONE);

                List<Data> responseDataList = response.body();

                for (Data data : responseDataList) {

                    dataList.add(data);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG,"apiCallData: ", t);
            }
        });

    }*/
}
