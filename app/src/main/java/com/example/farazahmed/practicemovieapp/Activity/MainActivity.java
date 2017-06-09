package com.example.farazahmed.practicemovieapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Adaptor.ViewPagerAdaptorMainActivity;
import com.example.farazahmed.practicemovieapp.Fragments.MoviesFragment;
import com.example.farazahmed.practicemovieapp.Model.AuthenticateSession;
import com.example.farazahmed.practicemovieapp.Model.AuthenticationToken;
import com.example.farazahmed.practicemovieapp.Model.Genre;
import com.example.farazahmed.practicemovieapp.Model.GenreObject;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitClient;
import com.example.farazahmed.practicemovieapp.Retrofit.RetrofitService;
import com.example.farazahmed.practicemovieapp.Utils.SharedPrefManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;

import android.widget.SearchView.OnQueryTextListener;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "46ad34bde863515d718c63e688c1c144";

    private SearchView searchView;
    private ImageView imdbimage;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String mUsername, uri;
    private CircleImageView userpic;
    Context mContext = this;

    private List<Genre> Genres = new ArrayList<>();
    private ViewPagerAdaptorMainActivity adapter;

    public List<Genre> getGenres() {
        return Genres;
    }

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPrefManager sharedPrefManager1 = new SharedPrefManager(getApplicationContext());
        sharedPrefManager1.checklogin();

        imdbimage = (ImageView) findViewById(R.id.imbdimage);
        searchView = (SearchView) findViewById(R.id.mainSearchView);
        // Set up the views.
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("TV Show"));

        //setting up view pager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdaptorMainActivity
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        loadGenre();
        userpic = (CircleImageView) findViewById(R.id.user_pic);
        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity.this, userpic, "userpicture");
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);

                }

            }
        });

        imdbimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MixMovies.class);
                startActivity(intent);
            }
        });

//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                for (UserInfo user : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
//                    if (user.getProviderId().equals("facebook.com")) {
//                        String facebookid = user.getUid();
//                        String photoUrl = "https://graph.facebook.com/" + facebookid + "/picture";
//                        Picasso.with(getApplicationContext()).load(photoUrl).placeholder(R.drawable.account_circle).into(userpic);
//                    } else if (user.getProviderId().equals("google.com")) {
//                        mUsername = user.getDisplayName();
//                        Uri uri = user.getPhotoUrl();
//                        Picasso.with(mContext)
//                                .load(uri)
//                                .placeholder(R.drawable.account_circle)
//                                .error(R.drawable.account)
//                                .into(userpic);
//                    } else if (user.getProviderId().equals("password")) {
//                    }
//                }
//            }
//        });
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//
//            }
//        };


    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void signOut() {
        new SharedPrefManager(mContext).clear();

        mAuth.signOut();

        LoginManager.getInstance().logOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void loadGenre() {
        //CREATING BASE URL
        RetrofitService apiservice = RetrofitClient.getRetrofitClient().create(RetrofitService.class);
        //ADDING QUERY PARAMETERS TO BASEURL
        Call<GenreObject> call = apiservice.getGenreMovies(API_KEY);
        //pb.setVisibility(View.VISIBLE);
        //TAKING RESPONSE BACK FROM BASEURL AND QUERY PARAMETERS
        call.enqueue(new Callback<GenreObject>() {
            @Override
            public void onResponse(Call<GenreObject> call, final Response<GenreObject> response) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Genres.clear();
                        Genres.addAll(response.body().getGenres());
                        viewPager.setAdapter(adapter);
                    }
                });

            }

            @Override
            public void onFailure(Call<GenreObject> call, Throwable t) {

            }
        });
    }





}