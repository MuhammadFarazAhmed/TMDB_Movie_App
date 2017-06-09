package com.example.farazahmed.practicemovieapp.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FarazAhmed on 5/22/2017.
 */

public class MixMoviesAdaptor extends RecyclerView.Adapter<MixMoviesAdaptor.MyViewHolder> {

    private Context context;
    private List<Movie> mixmovieslist;

    public MixMoviesAdaptor(Context context, List<Movie> mixmovieslist) {

        this.context = context;
        this.mixmovieslist = mixmovieslist;

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
        return mixmovieslist.get(position).getId();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);


        return new MyViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        Movie movie = mixmovieslist.get(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).fit().centerCrop().into(holder.IvMovieImage);
        holder.tvMovieName.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvRatingText.setText(String.format("%s", movie.getVoteAverage()));
        holder.tvGenres.setText(android.text.TextUtils.join(", ", movie.getGenreNames()));

    }

    @Override
    public int getItemCount() {
        return mixmovieslist.size();
    }
}

