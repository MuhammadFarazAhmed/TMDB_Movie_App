package com.example.farazahmed.practicemovieapp.Model;

import java.util.List;

/**
 * Created by FarazAhmed on 5/13/2017.
 */

public class RecommendObject {


    private Integer page;

    private List<RecommendedMovie> results;

    private Integer total_pages;

    private Integer total_results;

    public RecommendObject() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<RecommendedMovie> getResults() {
        return results;
    }

    public void setResults(List<RecommendedMovie> results) {
        this.results = results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }
}
