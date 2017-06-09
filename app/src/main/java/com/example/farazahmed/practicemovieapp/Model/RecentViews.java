package com.example.farazahmed.practicemovieapp.Model;

/**
 * Created by FarazAhmed on 5/15/2017.
 */

public class RecentViews {

    private int id;

    private String movietitle;

    private String moviereleasedate;

    private String movieratings;

    private String movieposter;

    public RecentViews() {

    }

    public RecentViews(int id, String movietitle, String moviereleasedate, String movieratings, String movieposter) {
        this.id = id;
        this.movietitle = movietitle;
        this.moviereleasedate = moviereleasedate;
        this.movieratings = movieratings;
        this.movieposter = movieposter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public void setMovietitle(String movietitle) {
        this.movietitle = movietitle;
    }

    public String getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(String moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public String getMovieratings() {
        return movieratings;
    }

    public void setMovieratings(String movieratings) {
        this.movieratings = movieratings;
    }

    public String getMovieposter() {
        return movieposter;
    }

    public void setMovieposter(String movieposter) {
        this.movieposter = movieposter;
    }
}
