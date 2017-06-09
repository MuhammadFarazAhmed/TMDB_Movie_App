package com.example.farazahmed.practicemovieapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Model.AuthenticateSession;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.example.farazahmed.practicemovieapp.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.http.Url;

import static com.example.farazahmed.practicemovieapp.Activity.SplashScreen.request_token;

public class Webview extends AppCompatActivity {

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";
    public static String  session_id;

    private WebView webview;

    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(this.getIntent().getDataString());

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if (url.contains("allow")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            makeUserSession();
                            Intent  intent = new Intent(Webview.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Welcome TMDB User", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);


                }
                return true;

            }
        });


    }

    private void makeUserSession() {
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        Call<AuthenticateSession> call = apiservice.getSessionId(request_token, API_KEY);

        call.enqueue(new Callback<AuthenticateSession>() {
            @Override
            public void onResponse(Call<AuthenticateSession> call, Response<AuthenticateSession> response) {
                session_id = response.body().getSession_id();

            }

            @Override
            public void onFailure(Call<AuthenticateSession> call, Throwable t) {

            }
        });


    }
}
