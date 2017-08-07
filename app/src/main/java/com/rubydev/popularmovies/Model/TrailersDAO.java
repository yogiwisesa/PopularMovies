package com.rubydev.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yogi Wisesa on 8/3/2017.
 */

public class TrailersDAO {

    /**
     * id : 211672
     * results : [{"id":"571b76d8c3a36864e00025a0","iso_639_1":"en","iso_3166_1":"US","key":"jc86EFjLFV4","name":"Official Trailer 2","site":"YouTube","size":1080,"type":"Trailer"},{"id":"571b7736c3a368243400130f","iso_639_1":"en","iso_3166_1":"US","key":"P9-FCC6I7u0","name":"Official Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"571b7752c3a368525f0058ad","iso_639_1":"en","iso_3166_1":"US","key":"Wfql_DoHRKc","name":"Official Trailer 3","site":"YouTube","size":1080,"type":"Trailer"}]
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
         * id : 571b76d8c3a36864e00025a0
         * iso_639_1 : en
         * iso_3166_1 : US
         * key : jc86EFjLFV4
         * name : Official Trailer 2
         * site : YouTube
         * size : 1080
         * type : Trailer
         */

        private String key;
        private String name;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
