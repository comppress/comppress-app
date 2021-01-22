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
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("contents")
    Call<List<Content>> getContents();

    @GET("data?listLength=50")
    Call<List<Data>> getData();

    @GET("ratedNews?listLength=20")
    Call<List<Data>> ratedNews();

    @GET("latestNews?listLength=20")
    Call<List<Data>> latestNews();

    @POST("postRating")
    Call<Void> getRating(@Body RatingPojo ratingPojo);

    @GET("userReference")
    Call<Long> sendUserReference(@Query("name") String name);

    @GET("day?listLength=50")
    Call<List<Data>> getNewsOfDay();

    @GET("articles?interval=week&listLength=50")
    Call<List<Data>> getNewsOfWeek();

    @GET("articles?interval=month&listLength=50")
    Call<List<Data>> getNewsOfMonth();
}
