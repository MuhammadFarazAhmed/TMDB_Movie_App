package com.example.farazahmed.practicemovieapp.Adaptor;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.farazahmed.practicemovieapp.Fragments.ViewpagerFragment;
import com.example.farazahmed.practicemovieapp.Model.Backdrops;
import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FarazAhmed on 5/10/2017.
 */

public class CustomImageAdaptor extends FragmentStatePagerAdapter {


    private List<Backdrops> backdropsList;


    public CustomImageAdaptor(FragmentManager fragmentManager, List<Backdrops> backdropsList) {
        super(fragmentManager);
        this.backdropsList = backdropsList;
    }

    @Override
    public int getCount() {
        return backdropsList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ViewpagerFragment.newInstance("https://image.tmdb.org/t/p/w500" + backdropsList.get(position).getFile_path());
    }

}
