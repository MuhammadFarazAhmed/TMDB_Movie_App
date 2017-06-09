package com.example.farazahmed.practicemovieapp.Adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.farazahmed.practicemovieapp.Fragments.SignInFragment;
import com.example.farazahmed.practicemovieapp.Fragments.SignUpFragment;

/**
 * Created by FarazAhmed on 4/26/2017.
 */

public class ViewPagerAdaptorLoginActivity extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdaptorLoginActivity(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SignInFragment tab1 = new SignInFragment();
                return tab1;
            case 1:
                SignUpFragment tab2 = new SignUpFragment();
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
