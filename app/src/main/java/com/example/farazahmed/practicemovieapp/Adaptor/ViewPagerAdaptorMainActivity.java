package com.example.farazahmed.practicemovieapp.Adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.farazahmed.practicemovieapp.Fragments.MoviesFragment;
import com.example.farazahmed.practicemovieapp.Fragments.SignInFragment;
import com.example.farazahmed.practicemovieapp.Fragments.SignUpFragment;
import com.example.farazahmed.practicemovieapp.Fragments.TVFragment;

/**
 * Created by FarazAhmed on 5/3/2017.
 */

public class ViewPagerAdaptorMainActivity extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdaptorMainActivity(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MoviesFragment tab1 = new MoviesFragment();
                return tab1;
            case 1:
                TVFragment tab2 = new TVFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
