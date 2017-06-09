package com.example.farazahmed.practicemovieapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarazAhmed on 5/3/2017.
 */

public class TVShow implements Serializable {


    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("id")
    private Integer id;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("origin_country")
    private List<String> originCountry;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();

    private List<String> genreNames = new ArrayList<>();
    @SerializedName("original_language")
    private String orignalLanguages;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("name")
    private String name;
    @SerializedName("original_name")
    private String originalName;



    public TVShow(String posterPath,String popularity,Integer id, String backdropPath,Double voteAverage, String firstAirDate, String originCountry,List<Integer> genreIds
                   ,String orignalLanguages,Integer voteCount,String name,String originalName ) {

    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setOrignalLanguages(String orignalLanguages) {
        this.orignalLanguages = orignalLanguages;
    }

    public String getOrignalLanguages() {
        return orignalLanguages;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }
}
