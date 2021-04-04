package org.comppress.android.api;

import org.comppress.android.json_model.Content;
import org.comppress.android.json_model.Feed;
import org.comppress.android.json_model.Rating;

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

    //@POST("postRating")
    //Call<Void> getRating(@Body RatingPojo ratingPojo);

    @POST("ratings")
    Call<Void> getRating(@Body Rating rating);

    @GET("userReference")
    Call<Long> sendUserReference(@Query("name") String name);

    //@GET("day")
    //Call<List<Data>> getNewsOfDay();
    //
    //@GET("articles?interval=week")
    //Call<List<Data>> getNewsOfWeek();
    //
    //@GET("articles?interval=month")
    //Call<List<Data>> getNewsOfMonth();

    @GET("feeds?timeFrame=day")
    Call<List<Feed>> getNewsOfDay();
    @GET("feeds?timeFrame=week")
    Call<List<Feed>> getNewsOfWeek();
    @GET("feeds?timeFrame=month")
    Call<List<Feed>> getNewsOfMonth();
}
