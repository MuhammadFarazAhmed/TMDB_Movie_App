package com.example.farazahmed.practicemovieapp.Model;

import com.facebook.internal.InternalSettings;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FarazAhmed on 5/6/2017.
 */

public class Genre {

  @SerializedName("id")
  private Integer id;
    @SerializedName("name")
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public int getId() {
        return id;
    }


}
