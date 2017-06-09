package com.example.farazahmed.practicemovieapp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Activity.LoginActivity;
import com.example.farazahmed.practicemovieapp.Activity.MainActivity;
import com.example.farazahmed.practicemovieapp.Adaptor.MoviesAdaptor;
import com.example.farazahmed.practicemovieapp.Adaptor.TVShowsAdaptor;
import com.example.farazahmed.practicemovieapp.Model.Genre;
import com.example.farazahmed.practicemovieapp.Model.GenreObject;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.MoviesObject;
import com.example.farazahmed.practicemovieapp.Model.TVObject;
import com.example.farazahmed.practicemovieapp.Model.TVShow;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.example.farazahmed.practicemovieapp.Utils.SpacesItemDecoration;
import com.example.farazahmed.practicemovieapp.Utils.Utils;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {
    private static final String TAG = MoviesFragment.class.getSimpleName();

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";
    private List<TVShow> tvshowlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private TextView emptyTextView;

    ProgressBar pb;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Genre> Genres = new ArrayList<>();

    private int currentPage = 0;
    private int totalPage = 0;
    private boolean isLoading = false;
    private boolean HadAllLoaded = false;

    Paginate.Callbacks callbacks = new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            isLoading = true;
            loadData();


        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return HadAllLoaded;
        }
    };

    public TVFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        final ImageView imageView = (ImageView) view.findViewById(R.id.image_show_empty);
        emptyTextView = (TextView) view.findViewById(R.id.tv_show_empty);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tvshow_swipetorefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tvshows);
        layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TVShowsAdaptor(getContext(), tvshowlist);
        recyclerView.setAdapter(adapter);

        //setting up empty view of recyclerview
        // setEmptyTextView();

        SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                currentPage = 0;
                HadAllLoaded = false;
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
                    loadData();
                }

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

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
        Paginate.with(recyclerView, callbacks)
                .setLoadingTriggerThreshold(0)
                .build();


        return view;
    }


    private void loadData() {
        currentPage++;
        //CREATING BASE URL
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);

        //ADDING QUERY PARAMETERS TO BASEURL
        Call<TVObject> call = apiservice.getTopRatedTV(API_KEY, currentPage);


        //TAKING RESPONSE BACK FROM BASEURL AND QUERY PARAMETERS
        call.enqueue(new Callback<TVObject>() {
            @Override
            public void onResponse(Call<TVObject> call, final Response<TVObject> response) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        final int oldsize = tvshowlist.size();
                        isLoading = false;
                        currentPage = response.body().getPage();
                        totalPage = response.body().getTotalPages();
                        if (currentPage == totalPage) {
                            HadAllLoaded = true;
                        }
                        if (currentPage == 1) {
                            tvshowlist.clear();
                        }
                        tvshowlist.addAll(setGenreNamesInList(response.body().getResults()));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
            public void onFailure(Call<TVObject> call, Throwable t) {

                isLoading = false;
                HadAllLoaded = true;
                adapter.notifyDataSetChanged();
            }
        });
    }

    private List<TVShow> setGenreNamesInList(List<TVShow> results) {
        List<Genre> Genres = ((MainActivity) getActivity()).getGenres();
        for (int i = 0; i < results.size(); i++) {
            TVShow show = results.get(i);
            List<String> genreNames = new ArrayList<>();
            for (int j = 0; j < show.getGenreIds().size(); j++) {
                int movieGenID = show.getGenreIds().get(j);
                for (int k = 0; k < Genres.size(); k++) {
                    int genID = Genres.get(k).getId();
                    if (movieGenID == genID) {
                        //Success
                        genreNames.add(Genres.get(k).getName());
                    }
                }
            }
            show.setGenreNames(genreNames);
            results.set(i, show);
        }
        return results;
    }


    @Override
    public void onStart() {
        super.onStart();

    }
}
