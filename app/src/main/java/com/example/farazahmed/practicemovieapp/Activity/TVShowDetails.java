package com.example.farazahmed.practicemovieapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.TVShow;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

public class TVShowDetails extends AppCompatActivity {

    private ImageView showposter ;
    private TextView tv_showname;
    private TextView tv_showoverview;
    private TextView tv_showrating;
    private TextView tv_showReleaseDate;
    private TextView tv_showGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.showdetails_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//           Intent intent = new Intent(MoviesDetails.this,MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        //getting info from recyclerview
        TVShow show = (TVShow) getIntent().getExtras().getSerializable("DATA");

        //show the getting info
        showposter = (ImageView)findViewById(R.id.iv_showdetails_showposter);
        tv_showname = (TextView)findViewById(R.id.tv_showdetails_showname);
        tv_showoverview = (TextView)findViewById(R.id.tv_showdetails_showoverview);
        tv_showrating = (TextView)findViewById(R.id.tv_showdetails_showrating);
        tv_showReleaseDate = (TextView)findViewById(R.id.tv_showdetails_showReleaseDate);
        tv_showGenre = (TextView)findViewById(R.id.tv_showdetails_showGenre);

        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+show.getPosterPath()).into(showposter);
        tv_showname.setText(show.getOriginalName());
        tv_showoverview.setText(show.getOverview());
        tv_showrating.setText(show.getVoteAverage().toString() + "/10");
        tv_showReleaseDate.setText(show.getFirstAirDate());
        tv_showGenre.setText(android.text.TextUtils.join(" | ",show.getGenreNames()));

    }
}
