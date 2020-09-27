package com.example.couscousapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couscousapp.R;
import com.example.couscousapp.activity.ReaderActivity;
import com.example.couscousapp.json_model.Content;
import com.example.couscousapp.json_model.ContentPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder>{


    private List<ContentPojo> contentList;
    private Context context;

    public ContentAdapter(List<ContentPojo> list, Context context) {
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

        ContentPojo contentPojo = contentList.get(position);
        Content content = contentPojo.getContent();

        holder.textViewTitle.setText(content.getTitle());
        holder.textViewCreationDate.setText(content.getCreationDate());
        holder.textViewSource.setText(contentPojo.getSource());

        String viewRating = "";
        if (contentPojo.getRating() != -1) {
            viewRating = contentPojo.getRating().toString();
        }
        holder.textViewRating.setText(viewRating);
        Picasso.get().
                load(content.getImageLink())
                //.placeholder(context.getResources().getDrawable(R.drawable.default_image))
                .error(R.drawable.default_image)
                .into(holder.imageViewContent);

        holder.contentCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ReaderActivity.class);
                intent.putExtra("external_url", contentList.get(position).getContent().getLink());
                intent.putExtra("article_id", contentList.get(position).getContent().getId());
                intent.putExtra("article_rating", contentList.get(position).getRating());
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
        private ImageView imageViewContent;
        private CardView contentCardView;



        public ContentViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewCreationDate = itemView.findViewById(R.id.text_view_date);
            textViewSource = itemView.findViewById(R.id.text_view_source);
            textViewRating = itemView.findViewById(R.id.text_view_rating);
            imageViewContent = itemView.findViewById(R.id.image_view_content);
            contentCardView = itemView.findViewById(R.id.card_view_landingpage);

        }


    }

}
