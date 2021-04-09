package org.comppress.android.api;

import org.comppress.android.json_model.Content;
import org.comppress.android.json_model.Feed;
import org.comppress.android.json_model.Rating;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("{language}/contents")
    Call<List<Content>> getContents(@Path("language") String language);

    //@GET("data?listLength=50")
    //Call<List<Data>> getData();

    //@GET("ratedNews")
    //Call<List<Data>> ratedNews();

    //@GET("latestNews")
    //Call<List<Data>> latestNews();

    //@POST("postRating")
    //Call<Void> getRating(@Body RatingPojo ratingPojo);

    @POST("{language}/ratings")
    Call<Void> getRating(@Body Rating rating, @Path("language") String language);

    @GET("{language}/userReference")
    Call<Long> sendUserReference(@Path("language") String language, @Query("name") String name);

    //@GET("day")
    //Call<List<Data>> getNewsOfDay();
    //
    //@GET("articles?interval=week")
    //Call<List<Data>> getNewsOfWeek();
    //
    //@GET("articles?interval=month")
    //Call<List<Data>> getNewsOfMonth();

    @GET("{language}/feeds?timeFrame=day")
    Call<List<Feed>> getNewsOfDay(@Path("language") String language);
    @GET("{language}/feeds?timeFrame=week")
    Call<List<Feed>> getNewsOfWeek(@Path("language") String language);
    @GET("{language}/feeds?timeFrame=month")
    Call<List<Feed>> getNewsOfMonth(@Path("language") String language);
}
