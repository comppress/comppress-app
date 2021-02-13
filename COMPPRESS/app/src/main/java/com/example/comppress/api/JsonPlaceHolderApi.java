package com.example.comppress.api;

import com.example.comppress.json_model.Content;
import com.example.comppress.json_model.Data;
import com.example.comppress.json_model.RatingPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("contents")
    Call<List<Content>> getContents();

    //@GET("data?listLength=50")
    //Call<List<Data>> getData();

    //@GET("ratedNews")
    //Call<List<Data>> ratedNews();

    //@GET("latestNews")
    //Call<List<Data>> latestNews();

    @POST("postRating")
    Call<Void> getRating(@Body RatingPojo ratingPojo);

    @GET("userReference")
    Call<Long> sendUserReference(@Query("name") String name);

    @GET("day")
    Call<List<Data>> getNewsOfDay();

    @GET("articles?interval=week")
    Call<List<Data>> getNewsOfWeek();

    @GET("articles?interval=month")
    Call<List<Data>> getNewsOfMonth();
}
