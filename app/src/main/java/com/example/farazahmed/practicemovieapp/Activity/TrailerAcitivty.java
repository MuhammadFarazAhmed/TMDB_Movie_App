package com.example.farazahmed.practicemovieapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.farazahmed.practicemovieapp.Constants;
import com.example.farazahmed.practicemovieapp.Model.Video;
import com.example.farazahmed.practicemovieapp.Model.VideoObject;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerAcitivty extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView videoView;
    private List<Video> videoList = new ArrayList<>();
    Video video;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_acitivty);
        initViews();
        getMovieTrailer();
    }

    private void initViews() {
        videoView = (YouTubePlayerView) findViewById(R.id.trailerview);
    }

    private void getMovieTrailer() {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<VideoObject> call = apiservice.getMovieVideo(MoviesDetails.id, Constants.API_KEY);
        call.enqueue(new Callback<VideoObject>() {
            @Override
            public void onResponse(Call<VideoObject> call, Response<VideoObject> response) {
                videoList.addAll(response.body().getResults());
                video = videoList.get(videoList.size() - 1);
                key = video.getKey();
                videoView.initialize(Constants.YOUTUBE_API_KEY, TrailerAcitivty.this);
            }

            @Override
            public void onFailure(Call<VideoObject> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(key);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReasson) {

        if (errorReasson.isUserRecoverableError()) {
            errorReasson.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format("Error initializing youtube player", errorReasson.toString());
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            videoView.initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }
}
