package com.example.couscousapp.api;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.couscousapp.R;
import com.example.couscousapp.json_model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;



public class ApiRepository {

    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    public ApiRepository (String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void apiCallGetRatedData(final ProgressBar progressBar, final RecyclerView.Adapter adapter, final List<Data> dataList) {

        Call<List<Data>> call = jsonPlaceHolderApi.getRatedData();
        // Cant run this on the UI Thread, Retrofit runs it for us on a background thread
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "apiCallData return Code != 200");
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
                Log.e(TAG, "apiCallData: ", t);
            }
        });
    }
}
