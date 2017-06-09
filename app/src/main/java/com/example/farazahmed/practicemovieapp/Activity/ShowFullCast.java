package com.example.farazahmed.practicemovieapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.farazahmed.practicemovieapp.Adaptor.CastAdaptor;
import com.example.farazahmed.practicemovieapp.Model.Cast;
import com.example.farazahmed.practicemovieapp.Model.CastObject;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowFullCast extends AppCompatActivity {

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";
    Movie data;


    private List<Cast> castList = new ArrayList<>();

    private RecyclerView cast_recyclerView;
    private LinearLayoutManager cast_layoutManager;
    private RecyclerView.Adapter cast_adaptor;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_cast);

       id = getIntent().getStringExtra("movie_id");
        initView();
        loadMovieCast();
        cast_layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cast_recyclerView.setLayoutManager(cast_layoutManager);
        cast_adaptor = new CastAdaptor(this, castList);
        cast_recyclerView.setAdapter(cast_adaptor);
    }

    private void initView() {
        cast_recyclerView = (RecyclerView) findViewById(R.id.rv_castlist_full);
    }


    private void loadMovieCast() {

        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<CastObject> call = apiservice.getMovieCast(id, API_KEY);
        call.enqueue(new Callback<CastObject>() {
            @Override
            public void onResponse(Call<CastObject> call, Response<CastObject> response) {

                castList.addAll(response.body().getCast());
                cast_adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CastObject> call, Throwable t) {

            }
        });
    }

}
