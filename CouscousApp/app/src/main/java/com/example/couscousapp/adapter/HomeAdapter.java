package com.example.couscousapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couscousapp.R;
import com.example.couscousapp.json_model.Data;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private Context context;
    private List<Data> dataList;
    private ContentAdapter horizontalAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int counter = 0;

    public HomeAdapter(List<Data> data, Context context) {
        this.dataList = data;
        this.context = context;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater.from(context).inflate(R.layout.row_layout_landingpage, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(theView);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        holder.horizontalManager.scrollToPosition(dataList.get(position).getPositionRatedNewsStart()-1);
        holder.textViewCategory.setText(dataList.get(position).getCategory());
        horizontalAdapter = new ContentAdapter(dataList.get(position).getListContent(), context);
        holder.recyclerViewHorizontal.setAdapter(horizontalAdapter);
        holder.recyclerViewHorizontal.setRecycledViewPool(recycledViewPool);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewHorizontal;
        private TextView textViewCategory;

        private LinearLayoutManager horizontalManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        public HomeViewHolder(View itemView) {
            super(itemView);
            recyclerViewHorizontal = itemView.findViewById(R.id.landingpage_recycler_view_horizontal);
            recyclerViewHorizontal.setHasFixedSize(true);
            recyclerViewHorizontal.setNestedScrollingEnabled(false);
            recyclerViewHorizontal.setLayoutManager(horizontalManager);
            recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());
            textViewCategory = itemView.findViewById(R.id.text_view_category);
        }
    }
}
