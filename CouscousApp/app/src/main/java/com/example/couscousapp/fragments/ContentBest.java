package com.example.couscousapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.couscousapp.R;
import com.example.couscousapp.adapter.HomeAdapter;
import com.example.couscousapp.api.JsonPlaceHolderApi;
import com.example.couscousapp.json_model.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class ContentBest extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_content, container, false);*/
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        recyclerView = rootView.findViewById(R.id.rv_landingpage);
        progressBar = rootView.findViewById(R.id.pb_landingpage);

        dataList = new ArrayList<>();
        adapter = new HomeAdapter(dataList, getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//Todo        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        apiCallData(jsonPlaceHolderApi);

        return rootView;
    }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



            }

    public ContentBest() {
        // Required empty public constructor
    }

    public void apiCallData(JsonPlaceHolderApi jsonPlaceHolderApi){

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

    }

}