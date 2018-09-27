package e.deedcorpsinc.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static e.deedcorpsinc.popularmovies.utilities.Constants.API_KEY;
import static e.deedcorpsinc.popularmovies.utilities.Constants.API_KEY_TO_COMPARE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.IMAGE_BASE_URL;
import static e.deedcorpsinc.popularmovies.utilities.Constants.IMAGE_SIZE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.LANGUAGE_QUERY_PARAMETER;
import static e.deedcorpsinc.popularmovies.utilities.Constants.LANGUAGE_STRING;
import static e.deedcorpsinc.popularmovies.utilities.Constants.MOVIEDB_BASE_URL;
import static e.deedcorpsinc.popularmovies.utilities.Constants.MOVIE_ENDPOINT;
import static e.deedcorpsinc.popularmovies.utilities.Constants.QUERY_PARAMETER;
import static e.deedcorpsinc.popularmovies.utilities.Constants.VIDEO_PATH;

public class NetworkUtils {
    private final static String TAG= NetworkUtils.class.getSimpleName();

//build URL using Uri.parse
    public static URL buildUrl(String choice){
            URL movieUrl= null;
            if (API_KEY.equals(API_KEY_TO_COMPARE)){
                Uri builtUri= Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                        .path(choice)
                        .appendQueryParameter(QUERY_PARAMETER, API_KEY)
                        .appendQueryParameter(LANGUAGE_QUERY_PARAMETER, LANGUAGE_STRING)
                        .build();
                try {
                    movieUrl = new URL(builtUri.toString());
//                return movieUrl;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else {
                Log.e(TAG, "Wrong API KEY, update key or recheck key from movieDB");
            }

        return movieUrl;
    }

    public static URL buildMyUrl(String id, String path){
        URL videoUrl= null;
        Uri builtVideoUri= Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .path(MOVIE_ENDPOINT + id + path)
                .appendQueryParameter(QUERY_PARAMETER, API_KEY)
                .appendQueryParameter(LANGUAGE_QUERY_PARAMETER, LANGUAGE_STRING)
                .build();
        try {
            videoUrl= new URL(builtVideoUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return videoUrl;
    }

    public static URL buildImageUrl(String imagePath){
        URL imageUrl= null;
        Uri builtImageUri= Uri.parse(IMAGE_BASE_URL).buildUpon()
                .path( IMAGE_SIZE + imagePath)
                .build();

        try {
            imageUrl= new URL(builtImageUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }

    public static String getResponsefromHttpsUrl(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection= (HttpsURLConnection) url.openConnection();
        try {
            InputStream inputStream= httpsURLConnection.getInputStream();

            Scanner scanner= new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return  null;
            }
        } finally {
            httpsURLConnection.disconnect();
        }

    }

    public static List<URL> makeImageUrlList(String moviesJsonResponse) {
        List<URL> imageUrlList = new ArrayList<>();
        if (moviesJsonResponse != null) {
            try {
                //fetch movie list
                JSONObject movies = new JSONObject(moviesJsonResponse);

                //fetch result array
                JSONArray result = movies.optJSONArray("results");

                //fetch poster path, convert to url and add to list
                for (int x = 0; x < result.length(); x++) {
                    JSONObject objectWithPoster = result.optJSONObject(x);
                    String imagePath = objectWithPoster.optString("poster_path");

                    URL imageURl = NetworkUtils.buildImageUrl(imagePath);
                    imageUrlList.add(imageURl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Null Json Response");
        }
        return imageUrlList;
    }


}
