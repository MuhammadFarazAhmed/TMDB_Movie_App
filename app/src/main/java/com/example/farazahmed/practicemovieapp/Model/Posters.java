package com.example.farazahmed.practicemovieapp.Model;

/**
 * Created by FarazAhmed on 5/10/2017.
 */

public class Posters {

    private Double aspect_ratio;

    private String file_path ;

    private Integer height ;

    private Integer vote_count;

    private Integer width;

    public Posters() {

    }

    public Posters(Double aspect_ratio, String file_path, Integer height, Integer vote_count, Integer width) {
        this.aspect_ratio = aspect_ratio;
        this.file_path = file_path;
        this.height = height;
        this.vote_count = vote_count;
        this.width = width;
    }

    public Double getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(Double aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
