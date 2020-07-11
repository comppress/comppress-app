package com.example.couscousapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder>{


    private List<Content> contentList;
    private Context context;

    public ContentAdapter(List<Content> list, Context context) {
        this.contentList = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ContentViewHolder holder, final int position) {

        Content content = contentList.get(position);

        holder.textViewTitle.setText(content.getTitle());
        holder.textViewCategory.setText(content.getCategory());

        Picasso.get().
                load(content.getImageLink())
                .into(holder.imageViewContent);

    }

    @Override
    public int getItemCount() {

        return contentList.size();

    }


    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewCategory;
        private ImageView imageViewContent;


        public ContentViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewCategory = itemView.findViewById(R.id.tv_genre);
            imageViewContent = itemView.findViewById(R.id.image_view_movie);

        }


    }

}
