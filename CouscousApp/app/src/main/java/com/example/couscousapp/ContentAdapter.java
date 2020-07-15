package com.example.couscousapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ContentViewHolder holder, final int position) {

        Content content = contentList.get(position);

        holder.textViewTitle.setText(content.getTitle());
        holder.textViewCreationDate.setText(content.getCreationDate());
        //holder.textViewSource.setText(content.getSource());
        //holder.textViewRating.setText(content.getRating());

        Picasso.get().
                load(content.getImageLink())
                .into(holder.imageViewContent);

        holder.contentCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ReaderActivity.class);
                intent.putExtra("external_url", contentList.get(position).getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return contentList.size();

    }


    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewCreationDate;
        //private TextView textViewSource;
        //private TextView textViewRating;
        private ImageView imageViewContent;
        private CardView contentCardView;



        public ContentViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewCreationDate = itemView.findViewById(R.id.text_view_date);
            //textViewSource = itemView.findViewById(R.id.text_view_source);
            //textViewRating = itemView.findViewById(R.id.text_view_rating);
            imageViewContent = itemView.findViewById(R.id.image_view_content);
            contentCardView = itemView.findViewById(R.id.card_view_landingpage);

        }


    }

}
