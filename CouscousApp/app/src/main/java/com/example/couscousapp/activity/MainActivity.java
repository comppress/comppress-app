package com.example.couscousapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.couscousapp.adapter.HomeAdapter;
import com.example.couscousapp.R;
import com.example.couscousapp.api.JsonPlaceHolderApi;
import com.example.couscousapp.json_model.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<Data> dataList;

    //android views
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Toolbar tbLandingPage = (Toolbar) findViewById(R.id.tb_landingpage);
        setSupportActionBar(tbLandingPage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        dataList = new ArrayList<>();

        adapter = new HomeAdapter(dataList, this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView = findViewById(R.id.rv_landingpage);
        progressBar = findViewById(R.id.pb_landingpage);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        apiCallData(jsonPlaceHolderApi);

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
                return super.onOptionsItemSelected(item);

        }
    }

    public void apiCallData(JsonPlaceHolderApi jsonPlaceHolderApi){

        Call<List<Data>> call = jsonPlaceHolderApi.getData();
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

    }
}
