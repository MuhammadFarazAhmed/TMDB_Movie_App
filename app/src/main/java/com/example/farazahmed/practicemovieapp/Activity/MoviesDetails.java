package com.example.farazahmed.practicemovieapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Adaptor.CastAdaptor;
import com.example.farazahmed.practicemovieapp.Adaptor.CustomImageAdaptor;
import com.example.farazahmed.practicemovieapp.Adaptor.RecommendAdaptor;
import com.example.farazahmed.practicemovieapp.Model.Backdrops;
import com.example.farazahmed.practicemovieapp.Model.Cast;
import com.example.farazahmed.practicemovieapp.Model.CastObject;
import com.example.farazahmed.practicemovieapp.Model.ImagesObject;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.MovieDetailsObject;
import com.example.farazahmed.practicemovieapp.Model.MovieRatingObject;
import com.example.farazahmed.practicemovieapp.Model.RecentViews;
import com.example.farazahmed.practicemovieapp.Model.RecommendObject;
import com.example.farazahmed.practicemovieapp.Model.RecommendedMovie;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.example.farazahmed.practicemovieapp.Utils.SharedPrefManager;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetails extends AppCompatActivity {
    private static final String TAG = MoviesDetails.class.getSimpleName();

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";
    Movie data;
    RecommendedMovie rData;
    private List<Backdrops> backdropsList = new ArrayList<>();

    ViewPager viewPager;
    CustomImageAdaptor adaptor;

    int screenWidth;
    int screenHeight;

    private ImageView ivmovieposter;
    private TextView tvmoviename;
    private TextView tvreleasedate;
    private TextView tvratings;
    private TextView tvtagline;
    private TextView tvoverview;

    List<Cast> num2 = new ArrayList<>();
    int currentPage = 0;
    Timer timer;

    private List<Cast> castList = new ArrayList<Cast>();
    private List<RecommendedMovie> recommendedMovies = new ArrayList<>();

    MovieDetailsObject movieDetailsObject = new MovieDetailsObject();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter Radaptor;

    private RecyclerView cast_recyclerView;
    private LinearLayoutManager cast_layoutManager;
    private RecyclerView.Adapter cast_adaptor;

    Dialog dialog;
    public static  String id = "";
    private Button showfullcast;
    private TextView rateMovieText;

    private ImageView rateMovie;
    private RatingBar ratingbar;
    private TextView ratingText;

    private Double ratings;

    private Button trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);


        int callingactivity = getIntent().getIntExtra("calling-activity", 0);
        if (callingactivity == 1002) {
            RecommendedMovie recommendedMovie = (RecommendedMovie) getIntent().getExtras().getSerializable("RDATA");
            id = recommendedMovie.getId().toString();
        }
        //getting movie id
        else {
            data = (Movie) getIntent().getExtras().getSerializable("MDATA");
            id = data.getId().toString();
            int movieid = data.getId();
            String moviename = data.getTitle();
            String moviereleasedate = data.getReleaseDate();
            String movieratings = data.getVoteAverage().toString();
            String movieposter = "https://image.tmdb.org/t/p/w500" + data.getPosterPath();

            SharedPreferences moviedetails = getApplicationContext().getSharedPreferences("recent", 0);
            SharedPreferences.Editor editor = moviedetails.edit();

            Gson gson1 = new Gson();
            String json1 = moviedetails.getString("recentsList", "[]");
            Type type = new TypeToken<List<RecentViews>>() {
            }.getType();
            List<RecentViews> recentViewsList = gson1.fromJson(json1, type);

            RecentViews recentViews = new RecentViews(movieid, moviename, moviereleasedate, movieratings, movieposter);

            if (recentViewsList == null || recentViewsList.size() == 0) {
                recentViewsList = new ArrayList<>();
                recentViewsList.add(recentViews);
            } else {
                boolean duplicate = false;
                for (int i = 0; i < recentViewsList.size(); i++) {
                    if (recentViewsList.get(i).getId() == recentViews.getId()) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    recentViewsList.add(0, recentViews);
                }
            }
            String json = gson1.toJson(recentViewsList);
            editor.putString("recentsList", json);
            editor.apply();

        }


        initViews();

        rateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(MoviesDetails.this);
                dialog.setContentView(R.layout.rating_bar_dialog);
                dialog.setCancelable(true);

                ratingbar = (RatingBar) dialog.findViewById(R.id.ratingbar);
                ratingText = (TextView) dialog.findViewById(R.id.rating_text);
                Button saverating = (Button) dialog.findViewById(R.id.save);
                Button removerating = (Button) dialog.findViewById(R.id.remove);

                ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        ratingText.setVisibility(View.VISIBLE);
                        ratingText.setText((int) ratingbar.getRating()+"");
                    }
                });

                saverating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateMovie.setImageResource(R.drawable.filled_star);
                        rateMovieText.setText(ratingText.getText() + "\n" + "You Rate This");
                        if(ratingText.getText()!=null && ratingText.getText() != "") {
                            ratings = Double.parseDouble(String.valueOf(ratingText.getText()));
                        }
                            setGuestMovieRatings();
                        setMovieRatings();
                        dialog.dismiss();
                    }
                });
                removerating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        rateMovie.setImageResource(R.drawable.empty_star);
                        rateMovieText.setText("Rate This Movie");
                    }
                });

                dialog.show();

            }
        });

        viewPager = (ViewPager) findViewById(R.id.imageslider_MovieDetails);
        adaptor = new CustomImageAdaptor(getSupportFragmentManager(), backdropsList);
        viewPager.setAdapter(adaptor);

        android.view.Display display = ((android.view.WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight() / 2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth, screenHeight);
        viewPager.setLayoutParams(layoutParams);


        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Radaptor = new RecommendAdaptor(this, recommendedMovies);
        recyclerView.setAdapter(Radaptor);

        cast_layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cast_recyclerView.setLayoutManager(cast_layoutManager);
        cast_adaptor = new CastAdaptor(this, num2);
        cast_recyclerView.setAdapter(cast_adaptor);
        cast_recyclerView.setNestedScrollingEnabled(false);

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Mytimetask(), 300, 2000);

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MoviesDetails.this, TrailerAcitivty.class);
                startActivity(intent);
            }
        });

        showfullcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviesDetails.this, ShowFullCast.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });

    }

    public class Mytimetask extends TimerTask {

        @Override
        public void run() {
            MoviesDetails.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(currentPage++, true);
                }
            });
        }
    }


    private void initViews() {
        ivmovieposter = (ImageView) findViewById(R.id.iv_movieposter_moviedetails);
        tvmoviename = (TextView) findViewById(R.id.tv_moviename_moviedetails);
        tvreleasedate = (TextView) findViewById(R.id.tv_releasedate_moviedetails);
        tvratings = (TextView) findViewById(R.id.tv_ratings_moviedetails);
        tvtagline = (TextView) findViewById(R.id.tv_tagline_moviedetails);
        tvoverview = (TextView) findViewById(R.id.tv_overview_moviedetails);
        recyclerView = (RecyclerView) findViewById(R.id.recommendmovies_recyclerview_moviedetails);
        cast_recyclerView = (RecyclerView) findViewById(R.id.rv_castlist);
        showfullcast = (Button) findViewById(R.id.showfullcast);
        rateMovie = (ImageView) findViewById(R.id.rate_Movie);
        rateMovieText = (TextView)findViewById(R.id.rate_Movie_text);
        trailer =(Button)findViewById(R.id.trailerbutton);
    }

    private void loadMovieDetails() {

        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<MovieDetailsObject> call = apiservice.getMovieDetails(id, API_KEY);
        call.enqueue(new Callback<MovieDetailsObject>() {
            @Override
            public void onResponse(Call<MovieDetailsObject> call, Response<MovieDetailsObject> response) {

                movieDetailsObject = response.body();
                Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + movieDetailsObject.getPoster_path()).fit().centerCrop().into(ivmovieposter);
                tvmoviename.setText(movieDetailsObject.getTitle());
                tvreleasedate.setText(movieDetailsObject.getRelease_date());
                tvratings.setText(movieDetailsObject.getVote_average().toString() + "/10");
                if (tvtagline != null) {
                    tvtagline.setText("\"" + movieDetailsObject.getTagline() + "\"");
                } else {
                    tvtagline.setText("");
                }
                tvoverview.setText(movieDetailsObject.getOverview());

            }

            @Override
            public void onFailure(Call<MovieDetailsObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefManager sharedpref = new SharedPrefManager(getBaseContext());
        sharedpref.clear();
    }

    private void loadMovieImages() {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<ImagesObject> call = apiservice.getMovieImages(id, API_KEY);
        call.enqueue(new Callback<ImagesObject>() {
            @Override
            public void onResponse(Call<ImagesObject> call, Response<ImagesObject> response) {
                backdropsList.addAll(response.body().getBackdrops());
                adaptor.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ImagesObject> call, Throwable t) {

            }
        });

    }

    private void loadMovieCast() {

        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<CastObject> call = apiservice.getMovieCast(id, API_KEY);
        call.enqueue(new Callback<CastObject>() {
            @Override
            public void onResponse(Call<CastObject> call, Response<CastObject> response) {


                castList.addAll(response.body().getCast());
                if (num2.size() != 0) {


                } else {
                    num2.add(castList.get(0));
                    num2.add(castList.get(1));
                    num2.add(castList.get(2));
                    num2.add(castList.get(3));
                    num2.add(castList.get(4));
                }


                cast_adaptor.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<CastObject> call, Throwable t) {
                Log.i(TAG, t.getMessage().toString());
            }
        });
    }

    private void loadRecommendedMovies() {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<RecommendObject> call = apiservice.getRecommendedMovies(id, API_KEY);
        call.enqueue(new Callback<RecommendObject>() {
            @Override
            public void onResponse(Call<RecommendObject> call, Response<RecommendObject> response) {

                recommendedMovies.addAll(response.body().getResults());
                Radaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RecommendObject> call, Throwable t) {

            }
        });
    }

    private void setGuestMovieRatings()
    {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<MovieRatingObject> call = apiservice.setMovieRatings(id , SplashScreen.guest_session_id, API_KEY , ratings);

        call.enqueue(new Callback<MovieRatingObject>() {
            @Override
            public void onResponse(Call<MovieRatingObject> call, Response<MovieRatingObject> response) {
             if(response.isSuccessful())
             {
                 Toast.makeText(getApplicationContext(),"Your rating is save",Toast.LENGTH_SHORT).show();
             }
            }

            @Override
            public void onFailure(Call<MovieRatingObject> call, Throwable t) {

            }
        });
    }

    private void setMovieRatings()
    {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<MovieRatingObject> call = apiservice.setMovieRatings(id , Webview.session_id, API_KEY , ratings);

        call.enqueue(new Callback<MovieRatingObject>() {
            @Override
            public void onResponse(Call<MovieRatingObject> call, Response<MovieRatingObject> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Your rating is save",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieRatingObject> call, Throwable t) {

            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
//        dialog.setMessage("Loading...");
//        dialog.show();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadMovieDetails();
                loadMovieImages();
                loadMovieCast();
                loadRecommendedMovies();

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
