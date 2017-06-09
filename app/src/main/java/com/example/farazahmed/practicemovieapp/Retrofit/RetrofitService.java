package com.example.farazahmed.practicemovieapp.Retrofit;

import com.example.farazahmed.practicemovieapp.Model.AuthenticateGuest;
import com.example.farazahmed.practicemovieapp.Model.AuthenticateSession;
import com.example.farazahmed.practicemovieapp.Model.AuthenticationToken;
import com.example.farazahmed.practicemovieapp.Model.CastObject;
import com.example.farazahmed.practicemovieapp.Model.Genre;
import com.example.farazahmed.practicemovieapp.Model.GenreObject;
import com.example.farazahmed.practicemovieapp.Model.ImagesObject;
import com.example.farazahmed.practicemovieapp.Model.Movie;
import com.example.farazahmed.practicemovieapp.Model.MovieDetailsObject;
import com.example.farazahmed.practicemovieapp.Model.MovieRatingObject;
import com.example.farazahmed.practicemovieapp.Model.MoviesObject;
import com.example.farazahmed.practicemovieapp.Model.PopularMovieObject;
import com.example.farazahmed.practicemovieapp.Model.RecommendObject;
import com.example.farazahmed.practicemovieapp.Model.TVObject;
import com.example.farazahmed.practicemovieapp.Model.VideoObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by FarazAhmed on 5/4/2017.
 */

public interface RetrofitService {


    @GET("movie/top_rated")
    Call<MoviesObject> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TVObject> getTopRatedTV(@Query("api_key") String api_key, @Query("page") int page);

    @GET("genre/movie/list")
    Call<GenreObject> getGenreMovies(@Query("api_key") String apikey);

    @GET("search/movie")
    Call<Movie> getMovie(@Query("api_key") String apikey, @Query("query") String moviename);

    @GET("movie/{movie_id}/images")
    Call<ImagesObject> getMovieImages(@Path("movie_id") String id,@Query("api_key") String apikey);


    @GET("movie/{movie_id}")
    Call<MovieDetailsObject> getMovieDetails(@Path("movie_id") String id,@Query("api_key") String apikey);

    @GET("movie/{movie_id}/credits")
    Call<CastObject> getMovieCast(@Path("movie_id") String id,@Query("api_key") String apikey);

    @GET("movie/{movie_id}/recommendations")
    Call<RecommendObject> getRecommendedMovies(@Path("movie_id") String id, @Query("api_key") String apikey);

    @GET("movie/popular")
    Call<MoviesObject> getPopularMovies(@Query("api_key") String apikey,@Query("page") int page);

    @GET("authentication/token/new")
    Call<AuthenticationToken> getResquestToken(@Query("api_key")String apikey);

    @GET("authentication/session/new")
    Call<AuthenticateSession> getSessionId(@Query("request_token") String requesttoken,@Query("api_key")String apikey);

    @GET("authentication/guest_session/new")
    Call<AuthenticateGuest> getGuestSessionId(@Query("api_key")String apikey);

    @POST("movie/{movie_id}/rating")
    @FormUrlEncoded
    Call<MovieRatingObject> setMovieRatings(@Path("movie_id") String id, @Query("guest_session_id") String guestid, @Query("api_key") String apikey, @Field("value") Double value);

    @GET("movie/{movie_id}/videos")
    Call<VideoObject> getMovieVideo(@Path("movie_id")String id,@Query("api_key")String apikey);
}
