package com.example.farazahmed.practicemovieapp.Model;

import java.util.List;

/**
 * Created by FarazAhmed on 5/10/2017.
 */

public class ImagesObject {

    private Integer id;

    private List<Backdrops> backdrops;

    private List<Posters> posters;

    public ImagesObject(Integer id, List<Backdrops> backdrops, List<Posters> posters) {
        this.id = id;
        this.backdrops = backdrops;
        this.posters = posters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Posters> getPosters() {
        return posters;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
    }
}
