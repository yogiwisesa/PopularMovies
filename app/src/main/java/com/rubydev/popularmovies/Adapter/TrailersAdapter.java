package com.rubydev.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubydev.popularmovies.Model.ReviewsDAO;
import com.rubydev.popularmovies.Model.TrailersDAO;
import com.rubydev.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogi Wisesa on 8/4/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TrailersDAO.ResultsBean> list = new ArrayList<>();

    public TrailersAdapter(Context context, ArrayList<TrailersDAO.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailers_item, parent, false);
        return new TrailersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.ViewHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + list.get(getAdapterPosition()).getKey())));
        }
    }
}