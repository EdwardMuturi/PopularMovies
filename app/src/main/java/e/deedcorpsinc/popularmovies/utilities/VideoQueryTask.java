package e.deedcorpsinc.popularmovies.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class VideoQueryTask extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... urls) {
        URL movieUrl = urls[0];
        String movieDBSearchResults = null;
        try {
            movieDBSearchResults = NetworkUtils.getResponsefromHttpsUrl(movieUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieDBSearchResults;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
