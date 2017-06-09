package com.example.farazahmed.practicemovieapp.Model;

/**
 * Created by FarazAhmed on 5/24/2017.
 */

public class AuthenticateSession {

    private boolean success;
    private String session_id;

    public AuthenticateSession(boolean success, String session_id) {
        this.success = success;
        this.session_id = session_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
