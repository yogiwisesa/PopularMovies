package com.rubydev.popularmovies.Internet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yogi Wisesa on 6/16/2017.
 */

public class Service {
    public static final String API_KEY = "-";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMG_URL = "http://image.tmdb.org/t/p/w342/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
