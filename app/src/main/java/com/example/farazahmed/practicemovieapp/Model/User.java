package com.example.farazahmed.practicemovieapp.Model;

import android.net.Uri;

/**
 * Created by FarazAhmed on 4/28/2017.
 */

public class User {

    private String username;

    private String email;

    private String photo;


    public User(String username, String email, String photo) {

        this.username = username;
        this.email = email;
        this.photo = photo;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
