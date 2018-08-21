package e.deedcorpsinc.popularmovies.utilities;

public class Constants {
//Movie URL details
    public final static String MOVIEDB_BASE_URL= "https://api.themoviedb.org";
//    public final static String MOVIEDB_BASE_PATH_MOVIE= "3/movie/";
    public final static String POPULAR_MOVIE= "3/movie/popular";
    public final static String LATEST_MOVIE= "3/movie/latest";
    public final static String TOP_RATED= "3/movie/top_rated";
    public final static String QUERY_PARAMETER= "api_key";
    public final static String API_KEY= "ENTER_YOUR_API_KEY_HERE";
    public final static String API_KEY_TO_COMPARE= "ENTER_YOUR_API_KEY_HERE";

    public static String JSON_RESPONSE;

    //image URL details
    public final static String IMAGE_BASE_URL= "https://image.tmdb.org";
    public final static String IMAGE_SIZE= "t/p/w185/";
    public static String POSTER_PATH= "";

    //optional parameters
    public final static String LANGUAGE_QUERY_PARAMETER= "language";
    public final static String LANGUAGE_STRING= "en-US";
    public final static String PAGE_QUERY_PARAMTER= "page";
    public final static String PAGE_STRING= "1";

    //API FIELDS
    public final static String FIELD_BACKDROP_PATH="backdrop_path";
    public final static String FIELD_TITLE="title";
}
