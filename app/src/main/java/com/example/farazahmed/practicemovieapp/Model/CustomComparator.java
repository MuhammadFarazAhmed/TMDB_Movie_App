package com.example.farazahmed.practicemovieapp.Model;

import java.util.Comparator;

/**
 * Created by FarazAhmed on 5/22/2017.
 */

public class CustomComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {

        Double r1 = o1.getVoteAverage();
        Double r2 = o2.getVoteAverage();

        return  r1.compareTo(r2) ;
    }
}
