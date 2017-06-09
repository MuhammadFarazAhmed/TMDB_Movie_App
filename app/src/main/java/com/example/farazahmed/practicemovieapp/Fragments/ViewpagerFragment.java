package com.example.farazahmed.practicemovieapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.farazahmed.practicemovieapp.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewpagerFragment extends Fragment {

    String path ;

    public ViewpagerFragment() {
        // Required empty public constructor
    }

    public static ViewpagerFragment newInstance(String path) {
        ViewpagerFragment viewpagerFragment = new ViewpagerFragment();
        Bundle args = new Bundle();
        args.putString("value",path);
        viewpagerFragment.setArguments(args);
        return viewpagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       path = getArguments().getString("value");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootview =  inflater.inflate(R.layout.fragment_viewpager, container, false);

        ImageView imageview = (ImageView)rootview.findViewById(R.id.imageview_MovieDetails);

        Picasso.with(getContext()).load(path).fit().centerCrop().into(imageview);

        return rootview;
    }


}
