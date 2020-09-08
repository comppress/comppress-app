package com.example.couscousapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.couscousapp.R;
import com.example.couscousapp.adapter.HomeAdapter;
import com.example.couscousapp.api.ApiRepository;
import com.example.couscousapp.json_model.Data;

import java.util.ArrayList;
import java.util.List;


public class ContentNew<val> extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> dataList;
    private SwipeRefreshLayout swipeRefreshLayout;

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


//Todo        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();

        final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
        apiRepository.apiCallGetNews(progressBar, adapter, dataList, "latestNews");

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Info", "onRefresh called from SwipeRefreshLayout");
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        //Todo myUpdateOperation();
                        //ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
                        apiRepository.apiCallGetNews(progressBar, adapter, dataList,"latestNews");
                    }
                }
        );
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ContentNew() {
        // Required empty public constructor
    }
}