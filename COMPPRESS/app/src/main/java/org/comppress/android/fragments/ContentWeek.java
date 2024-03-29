package org.comppress.android.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.comppress.android.R;
import org.comppress.android.adapter.HomeAdapter;
import org.comppress.android.api.ApiRepository;
import org.comppress.android.json_model.Feed;

import java.util.ArrayList;
import java.util.List;

public class ContentWeek <val> extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Feed> feedList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        recyclerView = rootView.findViewById(R.id.rv_landingpage);
        progressBar = rootView.findViewById(R.id.pb_landingpage);

        feedList = new ArrayList<>();
        adapter = new HomeAdapter(feedList, getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        final ApiRepository apiRepository = new ApiRepository(getResources().getString(R.string.base_url));
        apiRepository.apiCallGetNews(progressBar, adapter, feedList, "week");

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Info", "onRefresh called from SwipeRefreshLayout");
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        updateOperation();
                    }
                }
        );
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ContentWeek() {
        // Required empty public constructor
    }

    private void updateOperation(){
        ContentWeek fragmentWeek = new ContentWeek();
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragmentWeek);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
