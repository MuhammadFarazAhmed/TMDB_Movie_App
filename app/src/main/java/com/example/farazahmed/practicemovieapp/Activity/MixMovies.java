package com.example.farazahmed.practicemovieapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Adaptor.MixMoviesAdaptor;
import com.example.farazahmed.practicemovieapp.Adaptor.MoviesAdaptor;
import com.example.farazahmed.practicemovieapp.Fragments.MoviesFragment;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MixMovies extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager layoutManager;

    private List<Movie> topratedmovies = MoviesFragment.topratedmoviesList;
    private List<Movie> popularmovies = MoviesFragment.popularMoviesList;
    private List<Movie> mixMovieslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_movies);

        recyclerView = (RecyclerView) findViewById(R.id.rv_mixmoives);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MixMoviesAdaptor(this, mixMovieslist);
        recyclerView.setAdapter(adapter);

        preparedata();

    }


    private void preparedata() {
        for (int i = 0; i < topratedmovies.size(); i++) {
            for (int j = 0; j < popularmovies.size(); j++) {
                if (topratedmovies.get(i).getId().equals(popularmovies.get(j).getId())) {
                    mixMovieslist.add(popularmovies.get(j));
                }
            }
        }
        if (mixMovieslist.size() == 0) {
            Toast.makeText(this, "no dublicate", Toast.LENGTH_SHORT).show();
        }
        Collections.sort(mixMovieslist, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return Double.valueOf(o1.getVoteAverage()).compareTo(o2.getVoteAverage());
            }
        });
    }
}
