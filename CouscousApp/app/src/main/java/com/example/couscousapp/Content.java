package com.example.couscousapp;

import com.google.gson.annotations.SerializedName;

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

    public String getCreationDate() {
        return creationDate;
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
