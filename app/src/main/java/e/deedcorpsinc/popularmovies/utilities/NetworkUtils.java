package e.deedcorpsinc.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static e.deedcorpsinc.popularmovies.utilities.Constants.API_KEY;
import static e.deedcorpsinc.popularmovies.utilities.Constants.API_KEY_TO_COMPARE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.IMAGE_BASE_URL;
import static e.deedcorpsinc.popularmovies.utilities.Constants.IMAGE_SIZE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.LANGUAGE_QUERY_PARAMETER;
import static e.deedcorpsinc.popularmovies.utilities.Constants.LANGUAGE_STRING;
import static e.deedcorpsinc.popularmovies.utilities.Constants.MOVIEDB_BASE_URL;
import static e.deedcorpsinc.popularmovies.utilities.Constants.QUERY_PARAMETER;

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


}
