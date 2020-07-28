package com.example.couscousapp.json_model;

public class RatingPojo {

    private Rating rating;
    private String xyz;

    public RatingPojo(Rating rating) {
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}