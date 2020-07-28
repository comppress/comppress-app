package com.example.couscousapp.api;

import com.example.couscousapp.json_model.Content;
import com.example.couscousapp.json_model.Data;
import com.example.couscousapp.json_model.Rating;
import com.example.couscousapp.json_model.RatingPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("contents")
    Call<List<Content>> getContents();

    @GET("data?listLength=50")
    Call<List<Data>> getData();

    @POST("postRating")
    Call<Void> getRating(@Body RatingPojo ratingPojo);

}
