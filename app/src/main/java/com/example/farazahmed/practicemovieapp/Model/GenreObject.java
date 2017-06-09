package com.example.farazahmed.practicemovieapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FarazAhmed on 5/7/2017.
 */

public class GenreObject {


    @SerializedName("genres")
    private List<Genre> genres;

    public GenreObject(List<Genre> genres) {
        this.genres= genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
