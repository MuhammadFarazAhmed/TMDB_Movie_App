package com.example.farazahmed.practicemovieapp.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Model.RecentViews;
import com.example.farazahmed.practicemovieapp.Model.RecommendedMovie;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FarazAhmed on 5/15/2017.
 */

public class RecentViewsAdaptor extends RecyclerView.Adapter<RecentViewsAdaptor.MyViewHolder> {

    private Context context;
    private List<RecentViews> recentViewsList;

    public RecentViewsAdaptor(Context context, List<RecentViews> recentViewsList) {
        this.context = context;
        this.recentViewsList = recentViewsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView movieposter;
        private TextView moviename;
        private TextView movieReleaseDate;
        private TextView movieRatings;

        public MyViewHolder(View itemView) {
            super(itemView);

            movieposter = (ImageView)itemView.findViewById(R.id.iv_recent_MovieImage);
            moviename = (TextView)itemView.findViewById(R.id.tv_recent_Moviename);
            movieReleaseDate = (TextView)itemView.findViewById(R.id.tv_recent_ReleaseDate);
            movieRatings = (TextView)itemView.findViewById(R.id.tv_recent_RatingText);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentview_single_item,parent,false);
        return new MyViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RecentViews recentViews = recentViewsList.get(position);

        holder.moviename.setText(recentViews.getMovietitle());
        holder.movieReleaseDate.setText(recentViews.getMoviereleasedate());
        holder.movieRatings.setText(recentViews.getMovieratings().toString());
        Picasso.with(context).load(recentViews.getMovieposter()).into(holder.movieposter);


    }

    @Override
    public int getItemCount() {
        return recentViewsList.size();
    }
}
