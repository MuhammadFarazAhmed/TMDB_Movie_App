package com.example.farazahmed.practicemovieapp.Model;

/**
 * Created by FarazAhmed on 5/12/2017.
 */

public class MovieDetailsObject {



    private String poster_path;

    private Integer id;

    private String original_title;

    private String title;

    private String overview;

    private String release_date;

    private String tagline;

    private Double vote_average;

    public MovieDetailsObject() {
    }

    public MovieDetailsObject(String poster_path, Integer id, String original_title, String overview, String release_date, String tagline, Double vote_average) {
        this.poster_path = poster_path;
        this.id = id;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.tagline = tagline;
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }
}
