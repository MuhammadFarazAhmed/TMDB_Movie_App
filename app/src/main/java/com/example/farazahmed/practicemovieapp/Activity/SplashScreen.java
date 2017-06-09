package com.example.farazahmed.practicemovieapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.farazahmed.practicemovieapp.Model.AuthenticateGuest;
import com.example.farazahmed.practicemovieapp.Model.AuthenticateSession;
import com.example.farazahmed.practicemovieapp.Model.AuthenticationToken;
import com.example.farazahmed.practicemovieapp.Model.CastObject;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.example.farazahmed.practicemovieapp.Utils.SharedPrefManager;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";

    private boolean hasstared;
    private ImageView logo;
    private LinearLayout buttonslayout;
    private Button TMDB_user;
    private Button guest;

    boolean issuccess;
    String expired;
    static String request_token;

    public static String guest_session_id;

   private  Context context ;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);


        logo = (ImageView) findViewById(R.id.logo);
        buttonslayout = (LinearLayout) findViewById(R.id.buttons_layout);
        TMDB_user = (Button) findViewById(R.id.login_user);
        guest = (Button) findViewById(R.id.login_guest);

        getAuthenticationToken();
        makeGuestSession();


        TMDB_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SplashScreen.this, Webview.class);
                intent.setData(Uri.parse("https://www.themoviedb.org/authenticate/" + request_token));
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Welcome Guest", Toast.LENGTH_SHORT).show();
            }
        });

        if (!hasstared) {
            hasstared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    logo.setVisibility(View.VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(logo, "translationY", 0, -500f);
                    animator.setDuration(1000);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            buttonslayout.setVisibility(View.VISIBLE);
                            AnimatorSet set = new AnimatorSet();
                            ObjectAnimator animator1 = ObjectAnimator.ofFloat(buttonslayout, "alpha", 0, 1).setDuration(500);
                            animator1.setInterpolator(new AccelerateInterpolator());
                            set.play(animator1);
                            ObjectAnimator animator2 = ObjectAnimator.ofFloat(buttonslayout, "TranslationY", 0, -200f).setDuration(250);
                            set.play(animator2);
                            set.start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            }, 1000);


        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }


    private void getAuthenticationToken() {

        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<AuthenticationToken> call = apiservice.getResquestToken(API_KEY);
        call.enqueue(new Callback<AuthenticationToken>() {
            @Override
            public void onResponse(Call<AuthenticationToken> call, Response<AuthenticationToken> response) {

                issuccess = response.body().isSuccess();
                expired = response.body().getExpired_at();
                request_token = response.body().getRequest_token();
            }

            @Override
            public void onFailure(Call<AuthenticationToken> call, Throwable t) {

            }
        });
    }

    private void makeGuestSession() {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<AuthenticateGuest> call = apiservice.getGuestSessionId(API_KEY);

        call.enqueue(new Callback<AuthenticateGuest>() {
            @Override
            public void onResponse(Call<AuthenticateGuest> call, Response<AuthenticateGuest> response) {
                guest_session_id = response.body().getGuest_session_id();
                final SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
                sharedPrefManager.clear();
                sharedPrefManager.createGuestSession(guest_session_id);
                sharedPrefManager.saveToken(SplashScreen.this,guest_session_id);


            }

            @Override
            public void onFailure(Call<AuthenticateGuest> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
