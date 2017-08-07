package com.rubydev.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rubydev.popularmovies.Activity.DetailActivity;
import com.rubydev.popularmovies.Internet.Service;
import com.rubydev.popularmovies.Model.MovieDetail;
import com.rubydev.popularmovies.Model.MoviesDAO;
import com.rubydev.popularmovies.Model.ReviewsDAO;
import com.rubydev.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogi Wisesa on 8/3/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context context;
    private List<ReviewsDAO.ResultsBean> list = new ArrayList<>();

    public ReviewsAdapter(Context context, List<ReviewsDAO.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_item, parent, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, final int position) {
        holder.tvAuthor.setText(list.get(position).getAuthor());
        holder.tvReview.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAuthor, tvReview;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvReview = (TextView) itemView.findViewById(R.id.tvReview);
        }

        @Override
        public void onClick(View view) {

/*            String url = list.get(getAdapterPosition()).getUrl();
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
            customTabsIntent.launchUrl(context, Uri.parse(url));*/

        }
    }
}