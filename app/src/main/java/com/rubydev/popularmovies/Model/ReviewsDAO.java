package com.rubydev.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yogi Wisesa on 8/3/2017.
 */

public class ReviewsDAO {

    /**
     * id : 211672
     * page : 1
     * results : [{"id":"55a58e46c3a3682bb2000065","author":"Andres Gomez","content":"The minions are a nice idea and the animation and London recreation is really good, but that's about it.\r\n\r\nThe script is boring and the jokes not really funny.","url":"https://www.themoviedb.org/review/55a58e46c3a3682bb2000065"},{"id":"55e108c89251416c0b0006dd","author":"movizonline.com","content":"a nice idea and the animation.the new thing in animation field.a movie that every one should like an kid or old man.","url":"https://www.themoviedb.org/review/55e108c89251416c0b0006dd"}]
     * total_pages : 1
     * total_results : 2
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Parcelable {
        /**
         * id : 55a58e46c3a3682bb2000065
         * author : Andres Gomez
         * content : The minions are a nice idea and the animation and London recreation is really good, but that's about it.

         The script is boring and the jokes not really funny.
         * url : https://www.themoviedb.org/review/55a58e46c3a3682bb2000065
         */

        private String author;
        private String content;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}
