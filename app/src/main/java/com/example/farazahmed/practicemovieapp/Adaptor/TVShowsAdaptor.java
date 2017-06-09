package com.example.farazahmed.practicemovieapp.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.farazahmed.practicemovieapp.Activity.MoviesDetails;
import com.example.farazahmed.practicemovieapp.Activity.TVShowDetails;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.TVShow;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

/**
 * Created by FarazAhmed on 5/3/2017.
 */

public class TVShowsAdaptor extends RecyclerView.Adapter<TVShowsAdaptor.MyViewHolder> {


    private Context context;
    private List<TVShow> TVList;
    private Random mRandom = new Random();

    public TVShowsAdaptor(Context context, List<TVShow> TVList) {

        this.context = context;
        this.TVList = TVList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public ImageView IvTVshowImage;
        public TextView tvTVshowname;
        public TextView tvReleasedate;
        public TextView tvGenres;
        public TextView tvTatingtext;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,TVShowDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DATA",TVList.get(getAdapterPosition()));
                    intent.putExtras(bundle);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) context, IvTVshowImage,"showimage");
                        context.startActivity(intent,options.toBundle());
                    }
                    else
                    {
                        context.startActivity(intent);

                    }
                }
            });

            layout = (RelativeLayout) itemView.findViewById(R.id.fl_TVShow_Layout);
            IvTVshowImage = (ImageView) itemView.findViewById(R.id.IvTvshowListRow_TvShowImage);
            tvTVshowname = (TextView) itemView.findViewById(R.id.tvShowListRow_tvname);
            tvReleasedate = (TextView) itemView.findViewById(R.id.tvShowListRow_ReleaseDate);
            tvGenres = (TextView) itemView.findViewById(R.id.tvTvshowListRow_Genres);
            tvTatingtext = (TextView) itemView.findViewById(R.id.tvTVshowListRow_RatingText);
        }

    }

    @Override
    public TVShowsAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_list_row, parent, false);


        return new MyViewHolder(rootview);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        TVShow show = TVList.get(position);
        int h1 = 700;
        int h2 = 450;
        boolean isEven = false;
        //calculate if postion is even or odd
        //if even then change height to h1 else h2
        if(position % 2 == 0)
        {
            isEven = true;
            holder.layout.getLayoutParams().height = h1;
        }
        else
        {
            holder.layout.getLayoutParams().height = h2;
        }


        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + show.getPosterPath()).into(holder.IvTVshowImage);
        holder.tvTVshowname.setText(show.getOriginalName());
        holder.tvReleasedate.setText(show.getFirstAirDate());
        holder.tvTatingtext.setText(show.getVoteAverage().toString());
        holder.tvGenres.setText(android.text.TextUtils.join(", ",show.getGenreNames()));

    }


    protected int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt((max - min) + min) + min;
    }

    @Override
    public int getItemCount() {
        return TVList.size();
    }
}