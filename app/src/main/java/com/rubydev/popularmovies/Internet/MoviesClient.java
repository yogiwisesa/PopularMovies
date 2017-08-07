package com.rubydev.popularmovies.Internet;

import com.rubydev.popularmovies.Model.MoviesDAO;
import com.rubydev.popularmovies.Model.ReviewsDAO;
import com.rubydev.popularmovies.Model.TrailersDAO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Yogi Wisesa on 6/16/2017.
 */

public interface MoviesClient {
    @GET("movie/{sort}?api_key="+Service.API_KEY)
    Call<MoviesDAO> getMovie(
            @Path("sort") String order,
            @Query("page") int page
    );

    @GET("movie/{id}/reviews?api_key="+Service.API_KEY)
    Call<ReviewsDAO> getReviews(
            @Path("id") String id
    );

    @GET("movie/{id}/videos?api_key="+Service.API_KEY)
    Call<TrailersDAO> getTrailers(
            @Path("id") String id
    );
}
