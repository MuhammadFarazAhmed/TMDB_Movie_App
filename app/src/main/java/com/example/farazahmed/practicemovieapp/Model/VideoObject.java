package com.example.farazahmed.practicemovieapp.Model;

import java.util.List;

/**
 * Created by FarazAhmed on 5/26/2017.
 */

public class VideoObject {

    private int id;

    private List<Video> results;

    public VideoObject(int id, List<Video> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
