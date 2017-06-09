package com.example.farazahmed.practicemovieapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.farazahmed.practicemovieapp.Activity.SplashScreen;

/**
 * Created by FarazAhmed on 4/28/2017.
 */

public class SharedPrefManager {

    SharedPreferences sharedPreferences;
    Context mContext;
    // shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "sessionPref";
    private  static  final String SESSION_ID ="tmdb_id";
    private  static  final String GUEST_ID ="tmdb_id";
    SharedPreferences.Editor editor;

    boolean TMDBUSER = false;
    boolean guest = false;

    public SharedPrefManager (Context context) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    public void createSession(String session_id)
    {
        editor.putBoolean("IS_LOGGED_IN",true);
        editor.putString(SESSION_ID,session_id);
        editor.commit();
    }
    public void createGuestSession(String session_id)
    {
        editor.putBoolean("IS_LOGGED_IN",true);
        editor.putString(GUEST_ID,session_id);
        editor.commit();
    }

    public  void checklogin()
    {

        if(!getISLogged_IN())
        {
            Intent i = new Intent(mContext, SplashScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }


    public boolean getISLogged_IN() {
        //mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }

    public void saveToken(Context context, String token){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID_TOKEN", token);
        editor.apply();
    }

    public String getUserToken(){
        //mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("ID_TOKEN", "");
    }

    public void clear(){
        editor.clear();
        editor.apply();
    }
}
