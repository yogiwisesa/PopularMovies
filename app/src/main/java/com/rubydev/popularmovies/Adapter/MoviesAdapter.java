package com.rubydev.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rubydev.popularmovies.Activity.DetailActivity;
import com.rubydev.popularmovies.Internet.Service;
import com.rubydev.popularmovies.Model.MovieDetail;
import com.rubydev.popularmovies.Model.MoviesDAO;
import com.rubydev.popularmovies.R;
import com.rubydev.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogi Wisesa on 6/16/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter .ViewHolder> {

    private Context context;
    private List<MoviesDAO.ResultsBean> list = new ArrayList<>();

    public MoviesAdapter(Context context, List<MoviesDAO.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView ivPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            ivPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, DetailActivity.class);

                    MovieDetail detail = new MovieDetail(
                            "" + list.get(+getAdapterPosition()).getId(),
                            list.get(getAdapterPosition()).getTitle(),
                            list.get(getAdapterPosition()).getRelease_date(),
                            list.get(getAdapterPosition()).getVote_average(),
                            list.get(getAdapterPosition()).getOverview(),
                            list.get(getAdapterPosition()).getPoster_path());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("MovieDetail", detail);
                    i.putExtras(bundle);

                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(Service.IMG_URL+list.get(position).getPoster_path())
                .resize(400,600)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(List<MoviesDAO.ResultsBean> movies) {
        list.clear();
        list.addAll(movies);
        notifyDataSetChanged();
    }

    public void add(Cursor cursor) {
        list.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(MovieContract.MovieEntry.COL_MOVIE_ID);
                String title = cursor.getString(MovieContract.MovieEntry.COL_MOVIE_TITLE);
                String posterPath = cursor.getString(MovieContract.MovieEntry.COL_MOVIE_POSTER_PATH);
                String overview = cursor.getString(MovieContract.MovieEntry.COL_MOVIE_OVERVIEW);
                String rating = cursor.getString(MovieContract.MovieEntry.COL_MOVIE_VOTE_AVERAGE);
                String releaseDate = cursor.getString(MovieContract.MovieEntry.COL_MOVIE_RELEASE_DATE);
                MoviesDAO.ResultsBean movie = new MoviesDAO.ResultsBean(id, title, posterPath, overview, rating, releaseDate);
                list.add(movie);
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }

    public int getSize(){
        return list.size();
    }
}
