package com.example.farazahmed.practicemovieapp.Activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.farazahmed.practicemovieapp.Adaptor.RecentViewsAdaptor;
import com.example.farazahmed.practicemovieapp.Adaptor.RecommendAdaptor;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.RecentViews;
import com.example.farazahmed.practicemovieapp.R;
import com.example.farazahmed.practicemovieapp.Utils.SharedPrefManager;
import com.facebook.login.LoginManager;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String mUsername, mUseremail;
    public CircleImageView userpic;
    private TextView username;
    private TextView useremail;
    private FloatingActionButton logout;
    Context mContext = this;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter Radaptor;
    private List<RecentViews> recentViewsList = new ArrayList<>();
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    public static Uri uri;
    private CoordinatorLayout rootlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        rootlayout = (CoordinatorLayout) findViewById(R.id.root_layout);
        userpic = (CircleImageView) findViewById(R.id.iv_profile_userimage);
        username = (TextView) findViewById(R.id.tv_profile_user_name);
        useremail = (TextView) findViewById(R.id.tv_profile_user_email);
        logout = (FloatingActionButton) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popupimage, null);

                ImageView profilepic = (ImageView)container.findViewById(R.id.profilepic);
                profilepic.setImageDrawable(userpic.getDrawable());


                popupWindow = new PopupWindow(container, 1000, 1000, true);
                popupWindow.showAtLocation(rootlayout, Gravity.CENTER, 0, 0);

                dimBehind(popupWindow);



            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.rv_profile_Recent_Viewed);

        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Radaptor = new RecentViewsAdaptor(this, recentViewsList);
        recyclerView.setAdapter(Radaptor);

        preparedata();

    }
    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND; // add a flag here instead of clear others
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }


    private void preparedata() {
        SharedPreferences moviedetails = getApplicationContext().getSharedPreferences("recent", 0);
        Gson gson = new Gson();
        String json = moviedetails.getString("recentsList", "");
        Type type = new TypeToken<List<RecentViews>>() {
        }.getType();
        List<RecentViews> recentViews = gson.fromJson(json, type);

        if (recentViews != null) {
            recentViewsList.addAll(recentViews);
            Radaptor.notifyItemInserted(recentViewsList.size() - 1);

        }

    }

    private void signOut() {
        SharedPreferences moviedetails = getApplicationContext().getSharedPreferences("recent", 0);
        SharedPreferences.Editor editor = moviedetails.edit();
        editor.clear();
        editor.apply();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getBaseContext());
        sharedPrefManager.clear();
        Intent intent = new Intent(ProfileActivity.this, SplashScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
