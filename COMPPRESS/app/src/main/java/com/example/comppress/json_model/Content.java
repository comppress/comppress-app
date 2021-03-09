package com.example.comppress.json_model;

import android.text.format.DateUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private Long countRating;
    private Double averageRating;
    private Double sumRating;
    private String source;

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

    public Long getCountRating() {
        return countRating;
    }

    public void setCountRating(Long countRating) {
        this.countRating = countRating;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getSumRating() {
        return sumRating;
    }

    public void setSumRating(Double sumRating) {
        this.sumRating = sumRating;
    }

    public String getCreationDate() {
        if (creationDate == null) {
            Log.i("Info", "Creation date of content is null");
            return "Date null";
        }
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
        long diff = now-milliseconds;
        long format = 0;
        if (diff > DateUtils.WEEK_IN_MILLIS) {
            format = DateUtils.WEEK_IN_MILLIS;
        } else if (diff <= DateUtils.WEEK_IN_MILLIS && diff > DateUtils.DAY_IN_MILLIS) {
            format = DateUtils.DAY_IN_MILLIS;
        } else if (diff <= DateUtils.DAY_IN_MILLIS && diff > DateUtils.HOUR_IN_MILLIS) {
            format = DateUtils.HOUR_IN_MILLIS;
        } else if (diff <= DateUtils.HOUR_IN_MILLIS && diff > DateUtils.MINUTE_IN_MILLIS) {
            format = DateUtils.MINUTE_IN_MILLIS;
        } else if (diff <= DateUtils.MINUTE_IN_MILLIS) {
            format = DateUtils.SECOND_IN_MILLIS;
        }
        CharSequence parsedDate = DateUtils.getRelativeTimeSpanString(milliseconds, now, format);
        return parsedDate.toString();
    }

    public String getCategory() {
        return category;
    }

    public String getRssFeedId2() {
        return rssFeedId2;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
