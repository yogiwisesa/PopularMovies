package com.rubydev.popularmovies.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rubydev.popularmovies.Adapter.ReviewsAdapter;
import com.rubydev.popularmovies.Adapter.TrailersAdapter;
import com.rubydev.popularmovies.Internet.MoviesClient;
import com.rubydev.popularmovies.Model.MovieDetail;
import com.rubydev.popularmovies.Model.ReviewsDAO;
import com.rubydev.popularmovies.Model.TrailersDAO;
import com.rubydev.popularmovies.R;
import com.rubydev.popularmovies.Internet.Service;
import com.rubydev.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ArrayList<ReviewsDAO.ResultsBean> listReviews = new ArrayList<>();
    ArrayList<TrailersDAO.ResultsBean> listTrailers = new ArrayList<>();

    ReviewsAdapter reviewsAdapter;
    TrailersAdapter trailersAdapter;
    RecyclerView rvReview, rvTrailers;
    ScrollView svDetail;
    Button btFav;
    private RecyclerView.LayoutManager layoutManager;


    String sId, sTitle, sDate, sRatings, sSinopsis, mPosterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Movie MovieDetail");

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        MovieDetail movieDetail = (MovieDetail) bundle.getSerializable("MovieDetail");

        sId = movieDetail.getId();
        sTitle = movieDetail.getTitle();
        sDate = movieDetail.getDate();
        sRatings = movieDetail.getRating();
        sSinopsis = movieDetail.getSinopsis();
        mPosterPath = movieDetail.getPosterpath();

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvDate = (TextView) findViewById(R.id.tvReleaseDate);
        TextView tvRatings = (TextView) findViewById(R.id.tvRatings);
        TextView tvSinopsis = (TextView) findViewById(R.id.tvSynopsis);
        ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);

        tvTitle.setText(sTitle);
        tvDate.setText(sDate);
        tvRatings.setText(sRatings + "/10");
        tvSinopsis.setText(sSinopsis);

        rvReview = (RecyclerView) findViewById(R.id.rvReview);
        layoutManager = new LinearLayoutManager(this);
        rvReview.setHasFixedSize(true);
        rvReview.setLayoutManager(layoutManager);
        reviewsAdapter = new ReviewsAdapter(this, listReviews);
        rvReview.setAdapter(reviewsAdapter);
        rvReview.setNestedScrollingEnabled(false);


        svDetail = (ScrollView) findViewById(R.id.svDetail);

        rvTrailers = (RecyclerView) findViewById(R.id.rvTrailers);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvTrailers.setHasFixedSize(true);
        rvTrailers.setLayoutManager(lm);
        rvTrailers.setNestedScrollingEnabled(false);
        trailersAdapter = new TrailersAdapter(this, listTrailers);
        rvTrailers.setAdapter(trailersAdapter);


        if(savedInstanceState == null || !savedInstanceState.containsKey("listTrailer") || !savedInstanceState.containsKey("listReview")){
            loadTrailers(sId);
            loadReviews(sId);
        } else {
            listReviews = savedInstanceState.getParcelableArrayList("listReview");
            listTrailers = savedInstanceState.getParcelableArrayList("listTrailer");

            trailersAdapter = new TrailersAdapter(this, listTrailers);
            rvTrailers.setAdapter(trailersAdapter);

            reviewsAdapter = new ReviewsAdapter(this, listReviews);
            rvReview.setAdapter(reviewsAdapter);
        }

        Picasso.with(this)
                .load(Service.IMG_URL + mPosterPath)
                .resize(400, 600)
                .into(ivPoster);

        Log.i("Favorite:", "" + isFavorite());

        btFav = (Button) findViewById(R.id.btFav);
        if (!isFavorite()) {
            btFav.setText("Mark as Favorite");
        } else {
            btFav.setText("Remove from Favorites");
        }
        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite()) {
                    markAsFavorite();
                    btFav.setText("Remove from Favorites");
                } else {
                    removeFromFavorites();
                    btFav.setText("Mark as Favorite");
                }
                Log.i("Favorite:", "" + isFavorite());
            }
        });


    }

    void loadReviews(String id) {
        MoviesClient client = Service.createService(MoviesClient.class);
        Call<ReviewsDAO> call;

        call = client.getReviews(id);

        call.enqueue(new Callback<ReviewsDAO>() {
            @Override
            public void onResponse(Call<ReviewsDAO> call, Response<ReviewsDAO> response) {
                ReviewsDAO reviewsDAO = response.body();
                listReviews.addAll(reviewsDAO.getResults());
                rvReview.setAdapter(reviewsAdapter);
/*                if (loading != null) {
                    loading.dismiss();
                }*/

            }

            @Override
            public void onFailure(Call<ReviewsDAO> call, Throwable t) {
/*                if (loading != null) {
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(cl, "Loading Failed! \n" + t.toString(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }*/
            }

        });
    }

    void loadTrailers(String id) {
        MoviesClient client = Service.createService(MoviesClient.class);
        Call<TrailersDAO> call;

        call = client.getTrailers(id);

        call.enqueue(new Callback<TrailersDAO>() {
            @Override
            public void onResponse(Call<TrailersDAO> call, Response<TrailersDAO> response) {
                TrailersDAO trailersDAO = response.body();
                listTrailers.addAll(trailersDAO.getResults());
                rvTrailers.setAdapter(trailersAdapter);
/*                if (loading != null) {
                    loading.dismiss();
                }*/

            }

            @Override
            public void onFailure(Call<TrailersDAO> call, Throwable t) {
/*                if (loading != null) {
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(cl, "Loading Failed! \n" + t.toString(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }*/
            }

        });
    }

    boolean isFavorite() {
        Cursor movieCursor = DetailActivity.this.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry.COLUMN_MOVIE_ID},
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + sId,
                null,
                null);

        if (movieCursor != null && movieCursor.moveToFirst()) {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }

    public void markAsFavorite() {
        if (!isFavorite()) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                    sId);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                    sTitle);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH,
                    mPosterPath);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                    sSinopsis);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                    sRatings);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                    sDate);
            DetailActivity.this.getContentResolver().insert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    movieValues
            );

        }
    }

    public void removeFromFavorites() {
        if (isFavorite()) {
            DetailActivity.this.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + sId, null);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("ARTICLE_SCROLL_POSITION", new int[]{svDetail.getScrollX(), svDetail.getScrollY()});
        outState.putParcelableArrayList("listReview", listReviews);
        outState.putParcelableArrayList("listTrailer", listTrailers);
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if (position != null)
            svDetail.post(new Runnable() {
                public void run() {
                    svDetail.scrollTo(position[0], position[1]);
                }
            });
    }
}
