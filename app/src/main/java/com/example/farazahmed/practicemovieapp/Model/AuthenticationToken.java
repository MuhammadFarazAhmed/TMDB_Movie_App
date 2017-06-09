package com.example.farazahmed.practicemovieapp.Model;

/**
 * Created by FarazAhmed on 5/24/2017.
 */

public class AuthenticationToken {

    private boolean success;

    private String expired_at;

    private String request_token;

    public AuthenticationToken(boolean success, String expired_at, String request_token) {
        this.success = success;
        this.expired_at = expired_at;
        this.request_token = request_token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
