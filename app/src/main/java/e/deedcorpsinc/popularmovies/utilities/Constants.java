package e.deedcorpsinc.popularmovies.utilities;

import android.os.Build;

import e.deedcorpsinc.popularmovies.BuildConfig;

public class Constants {
//Movie URL details
    public final static String MOVIEDB_BASE_URL= "https://api.themoviedb.org";
//    public final static String MOVIEDB_BASE_PATH_MOVIE= "3/movie/";
    public final static String POPULAR_MOVIE= "3/movie/popular";
    public final static String LATEST_MOVIE= "3/movie/latest";
    public final static String TOP_RATED= "3/movie/top_rated";
    public final static String QUERY_PARAMETER= "api_key";
    public final static String API_KEY= BuildConfig.API_KEY;
    public final static String API_KEY_TO_COMPARE= BuildConfig.API_KEY_TO_COMPARE;
    public static final String KEY_REVIEWS = "REVIEWS";

    public static String JSON_RESPONSE;

    //image URL details
    public final static String IMAGE_BASE_URL= "https://image.tmdb.org";
    public final static String IMAGE_SIZE= "t/p/w185/";
    public static String POSTER_PATH= "";
    //video endpoint
    public static String movieId= "id";
    public static final String MOVIE_ENDPOINT= "3/movie/";
    public static final String VIDEO_PATH= "/videos";
    public static final String REVIEWS_PATH= "/reviews";

    //optional parameters
    public final static String LANGUAGE_QUERY_PARAMETER= "language";
    public final static String LANGUAGE_STRING= "en-US";
    public final static String PAGE_QUERY_PARAMTER= "page";
    public final static String PAGE_STRING= "1";

    //API FIELDS
    public final static String FIELD_BACKDROP_PATH="backdrop_path";
    public final static String FIELD_TITLE="title";
}
