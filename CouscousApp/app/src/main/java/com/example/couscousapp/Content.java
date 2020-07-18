package com.example.couscousapp;

import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Content {

    private Integer id;
    private String link;
    private String title;
    private String imageLink;
    private String description;
    private String creationDate;
    private String category;
    @SerializedName("rssFeedId")
    private String rssFeedId2;
    //private String source;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRssFeedId2(String rssFeedId2) {
        this.rssFeedId2 = rssFeedId2;
    }

    //public void setSource(String source) {
    //  this.source = source;
    //  }

    public Integer getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDescription() {
        return description;
    }

    public CharSequence getCreationDate() {
        String string_date = creationDate;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long milliseconds = 0;
        try {
            Date d = f.parse(string_date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();
        CharSequence parsedDate = DateUtils.getRelativeTimeSpanString(milliseconds, now, DateUtils.WEEK_IN_MILLIS);
        return parsedDate;
    }

    public String getCategory() {
        return category;
    }

    public String getRssFeedId2() {
        return rssFeedId2;
    }

    //public String getSource() {
    //  return source;
    //  }
}
