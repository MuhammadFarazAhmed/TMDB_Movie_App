package com.example.farazahmed.practicemovieapp.Model;

import java.util.List;

/**
 * Created by FarazAhmed on 5/12/2017.
 */

public class CastObject {

    private Integer id;

    private List<Cast> cast ;

    public CastObject() {
    }

    public CastObject(Integer id, List<Cast> cast) {
        this.id = id;
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
