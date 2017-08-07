package com.rubydev.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Yogi Wisesa on 6/20/2017.
 */

public class MovieDetail implements Serializable{
    String id, title, date, rating, sinopsis, posterpath;

    public MovieDetail(String id, String title, String date, String rating, String sinopsis, String posterpath) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.sinopsis = sinopsis;
        this.posterpath = posterpath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

}
