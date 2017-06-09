package com.example.farazahmed.practicemovieapp.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Activity.MoviesDetails;
import com.example.farazahmed.practicemovieapp.Model.RecommendedMovie;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by FarazAhmed on 5/13/2017.
 */

public class RecommendAdaptor extends RecyclerView.Adapter<RecommendAdaptor.MyViewHolder> {

    private Context context;
    private List<RecommendedMovie> recommendedMovieList;

    public RecommendAdaptor(Context context, List<RecommendedMovie> recommendedMovieList) {
        this.context = context;
        this.recommendedMovieList = recommendedMovieList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieposter;
        private TextView moviename;
        private TextView movieReleaseDate;
        private TextView movieRatings;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MoviesDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("RDATA", recommendedMovieList.get(getAdapterPosition()));
                    intent.putExtra("calling-activity", 1002);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

            movieposter = (ImageView) itemView.findViewById(R.id.iv_recommend_MovieImage);
            moviename = (TextView) itemView.findViewById(R.id.tv_recommend_Moviename);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.tv_recommend_ReleaseDate);
            movieRatings = (TextView) itemView.findViewById(R.id.tv_recommend_RatingText);
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_single_item, parent, false);

        return new MyViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        RecommendedMovie recommendedMovie = recommendedMovieList.get(position);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + recommendedMovie.getPoster_path()).fit().centerCrop().into(holder.movieposter);
        holder.moviename.setText(recommendedMovie.getTitle());
        holder.movieReleaseDate.setText(recommendedMovie.getRelease_date());
        holder.movieRatings.setText(recommendedMovie.getVote_average().toString());
    }

    @Override
    public int getItemCount() {
        return recommendedMovieList.size();
    }
}
