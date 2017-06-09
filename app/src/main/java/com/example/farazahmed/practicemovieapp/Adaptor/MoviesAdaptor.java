package com.example.farazahmed.practicemovieapp.Adaptor;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Activity.MoviesDetails;
import com.example.farazahmed.practicemovieapp.Fragments.MoviesFragment;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.RecentViews;
import com.example.farazahmed.practicemovieapp.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by FarazAhmed on 5/3/2017.
 */

public class MoviesAdaptor extends RecyclerView.Adapter<MoviesAdaptor.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MoviesAdaptor(Context context, List<Movie> movieList) {

        this.context = context;
        this.movieList = movieList;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView IvMovieImage;
        private TextView tvMovieName;
        private TextView tvReleaseDate;
        private TextView tvGenres;
        private ImageView IvRatingImage;
        private TextView tvRatingText;


        MyViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MoviesDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("MDATA", movieList.get(getAdapterPosition()));
                    intent.putExtra("calling-activity", 1001);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            IvMovieImage = (ImageView) itemView.findViewById(R.id.IvMovieListRow_MovieImage);
            tvMovieName = (TextView) itemView.findViewById(R.id.tvMovieListRow_Moviename);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tvMovieListRow_ReleaseDate);
            tvGenres = (TextView) itemView.findViewById(R.id.tvMovieListRow_Genres);
            IvRatingImage = (ImageView) itemView.findViewById(R.id.IvMovieListRow_RatingImage);
            tvRatingText = (TextView) itemView.findViewById(R.id.tvMovieListRow_RatingText);
        }


    }


    @Override
    public long getItemId(int position) {
        return movieList.get(position).getId();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);


        return new MyViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        Movie movie = movieList.get(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).fit().centerCrop().into(holder.IvMovieImage);
        holder.tvMovieName.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvRatingText.setText(String.format("%s", movie.getVoteAverage()));
        holder.tvGenres.setText(android.text.TextUtils.join(", ", movie.getGenreNames()));

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
