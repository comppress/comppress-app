package com.example.comppress.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comppress.R;
import com.example.comppress.activity.ReaderActivity;
import com.example.comppress.json_model.Content;
import com.example.comppress.json_model.ContentPojo;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ContentViewHolder holder, final int position) {

        Content content = contentList.get(position);

        holder.textViewTitle.setText(content.getTitle());
        // holder.textViewTitle.setText(content.getImageLink()); //Check image link
        holder.textViewCreationDate.setText(content.getCreationDate());
        //TODO content.getSource()
        holder.textViewSource.setText(content.getSource());

        //TODO
        if (content.getAverageRating() == 0) {
            holder.textViewCountRatings.setText("");
        } else {
            holder.textViewCountRatings.setText(content.getCountRating().toString());
        }

        if (content.getAverageRating() == 0) {
            holder.textViewRating.setText("Neu");
        } else {
            holder.textViewRating.setText(content.getAverageRating().toString().substring(0,3));
        }

        if (content.getImageLink().equals("not implemented")) {
            //TODO content.getSource()
            holder.textViewMissingImage.setText(content.getSource());
        } else {
            holder.textViewMissingImage.setText("");
        }


        Picasso.get().
                load(content.getImageLink())
                //.placeholder(context.getResources().getDrawable(R.drawable.default_image))
                .fit().centerCrop()
                //.error(R.drawable.default_image)
                .into(holder.imageViewContent);

        holder.contentCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ReaderActivity.class);
                intent.putExtra("external_url", contentList.get(position).getLink());
                intent.putExtra("article_id", contentList.get(position).getId());
                intent.putExtra("article_rating", contentList.get(position).getAverageRating());
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
        private TextView textViewSource;
        private TextView textViewRating;
        private TextView textViewCountRatings;
        private TextView textViewMissingImage;
        private ImageView imageViewContent;
        private CardView contentCardView;



        public ContentViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewCreationDate = itemView.findViewById(R.id.text_view_date);
            textViewSource = itemView.findViewById(R.id.text_view_source);
            textViewRating = itemView.findViewById(R.id.text_view_rating);
            textViewCountRatings = itemView.findViewById(R.id.text_view_count_ratings);
            imageViewContent = itemView.findViewById(R.id.image_view_content);
            contentCardView = itemView.findViewById(R.id.card_view_landingpage);
            textViewMissingImage = itemView.findViewById(R.id.text_view_missing_image);
        }


    }
}
