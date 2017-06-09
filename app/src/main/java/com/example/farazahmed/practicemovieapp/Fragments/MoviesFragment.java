package com.example.farazahmed.practicemovieapp.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Activity.MainActivity;
import com.example.farazahmed.practicemovieapp.Adaptor.MoviesAdaptor;
import com.example.farazahmed.practicemovieapp.Model.CustomComparator;
import com.example.farazahmed.practicemovieapp.Model.Genre;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.MoviesObject;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.example.farazahmed.practicemovieapp.Utils.Utils;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private static final String TAG = MoviesFragment.class.getSimpleName();

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    public static List<Movie> topratedmoviesList = new ArrayList<>();
    public  static List<Movie> popularMoviesList = new ArrayList<>();
    private List<Movie> finalmovies = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;


    private TextView emptyTextView;

    private int currentPage = 0;
    private int popularPage = 0;
    private int totalPage = 0;
    private boolean isLoading = false;
    private boolean HasLoadAll = false;

    Paginate.Callbacks callbacks = new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            isLoading = true;
            loadTopRatedMovies();
            loadPopularMovies();

        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return HasLoadAll;
        }
    };

    ProgressBar pb;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        final ImageView imageView = (ImageView) view.findViewById(R.id.image_movie_empty);
        emptyTextView = (TextView) view.findViewById(R.id.tv_movie_empty);
        //INITIALIZING PROGRESS BAR
        // pb = (ProgressBar)view.findViewById(R.id.pb_moviesfragment);

        //INITIALIZING SWIPE_REFRESH_LAYOUT
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.movies_swipetorefresh);

        //INITIALIZING RECYCLERVIEW
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_movies);
        layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        adapter = new MoviesAdaptor(getContext(), topratedmoviesList);
        recyclerView.setAdapter(adapter);


        //SETTING UP SWIPE_REFRESH_LISTENER
        SwipeRefreshLayout.OnRefreshListener onRefreshlistener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                popularPage = 0;
                HasLoadAll = false;
                Utils utils = new Utils(getContext());
                if (utils.isNetworkAvailable() == false) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    emptyTextView.setText("Connect to internet and Swipe to Refresh");
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loadTopRatedMovies();
                    loadPopularMovies();

                    adapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        //SET THE ABOVE LISTENER TO MOVIES SWIPE_REFRESH_LAYOUT
        swipeRefreshLayout.setOnRefreshListener(onRefreshlistener);

        Utils utils = new Utils(getContext());
        if (utils.isNetworkAvailable() == false) {
            emptyTextView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setText("Connect to internet and Swipe to Refresh");
        } else {
            emptyTextView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        //APPLYING PAGINATION
        Paginate.with(recyclerView, callbacks)
                .setLoadingTriggerThreshold(0)
                .build();


        return view;
    }


    private void loadTopRatedMovies() {
        currentPage++;
        //CREATING BASE URL
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);

        //ADDING QUERY PARAMETERS TO BASEURL
        Call<MoviesObject> call = apiservice.getTopRatedMovies(API_KEY, currentPage);

        //pb.setVisibility(View.VISIBLE);
        //TAKING RESPONSE BACK FROM BASEURL AND QUERY PARAMETERS
        call.enqueue(new Callback<MoviesObject>() {

            @Override
            public void onResponse(Call<MoviesObject> call, final Response<MoviesObject> response) {
                //pb.setVisibility(View.GONE);
                /*TODO in background*/
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        final int oldsize = topratedmoviesList.size();
                        isLoading = false;
                        currentPage = response.body().getPage();
                        totalPage = response.body().getTotalPages();

                        if (currentPage == totalPage) {
                            HasLoadAll = true;
                        }
                        if (currentPage == 1) {
                            topratedmoviesList.clear();
                        }

                        topratedmoviesList.addAll(setGenreNamestopratedInList(response.body().getResults()));


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*TODO after background job is done*/
                                if (currentPage == 1) {
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.notifyItemRangeChanged(oldsize - 1, response.body().getResults().size());
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<MoviesObject> call, Throwable t) {
                isLoading = false;
                HasLoadAll = true;
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void loadPopularMovies() {
        popularPage++;
        //CREATING BASE URL
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);

        //ADDING QUERY PARAMETERS TO BASEURL
        Call<MoviesObject> call = apiservice.getPopularMovies(API_KEY, currentPage);

        call.enqueue(new Callback<MoviesObject>() {
            @Override
            public void onResponse(Call<MoviesObject> call, final Response<MoviesObject> response) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        final int oldsize = popularMoviesList.size();
                        popularPage = response.body().getPage();
                        totalPage = response.body().getTotalPages();

                        if (popularPage == totalPage) {
                            HasLoadAll = true;
                        }
                        if (popularPage == 1) {
                            popularMoviesList.clear();
                        }
                        popularMoviesList.addAll(setGenreNamestopratedInList(response.body().getResults()));

                        if(popularPage == 1)
                        {
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            adapter.notifyItemRangeChanged(oldsize - 1, response.body().getResults().size());
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<MoviesObject> call, Throwable t) {

            }
        });


    }


    private List<Movie> setGenreNamestopratedInList(List<Movie> results) {
        List<Genre> Genres = ((MainActivity) getActivity()).getGenres();
        for (int i = 0; i < results.size(); i++) {
            Movie movie = results.get(i);
            List<String> genreNames = new ArrayList<>();
            for (int j = 0; j < movie.getGenreIds().size(); j++) {
                int movieGenID = movie.getGenreIds().get(j);
                for (int k = 0; k < Genres.size(); k++) {
                    int genID = Genres.get(k).getId();
                    if (movieGenID == genID) {
                        //Success
                        genreNames.add(Genres.get(k).getName());
                    }
                }
            }
            movie.setGenreNames(genreNames);
            results.set(i, movie);
        }
        return results;
    }

    @Override
    public void onStart() {
        super.onStart();
        //loadGenre();
    }
}
