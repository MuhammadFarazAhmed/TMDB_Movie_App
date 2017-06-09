package com.example.farazahmed.practicemovieapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FarazAhmed on 5/4/2017.
 */

public class RetrofitClient {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient()
    {
          if(retrofit == null) {

              retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                      .addConverterFactory(GsonConverterFactory.create()).build();

          }
        return retrofit;
    }
}
