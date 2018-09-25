package e.deedcorpsinc.popularmovies.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class MovieDBQueryTask extends AsyncTask<URL, Void, String> {
    private final URL passedURL;
    public AsyncResponse delegate = null;
    int requestCode;
    private static final String TAG= MovieDBQueryTask.class.getSimpleName();

    public MovieDBQueryTask(URL chosenURL) {
        this.passedURL= chosenURL;
    }

    @Override
    protected String doInBackground(URL... urls) {
//            URL movieUrl = urls[0];
        URL movieUrl = passedURL;
        String movieDBSearchResults = null;
        try {
            movieDBSearchResults = NetworkUtils.getResponsefromHttpsUrl(movieUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieDBSearchResults;
    }

    public MovieDBQueryTask setListener(AsyncResponse asyncResponse, int requestCode){
        this.delegate= asyncResponse;
        this.requestCode= requestCode;
        return this;
    }

    public MovieDBQueryTask setListener(AsyncResponse asyncResponseY){
        this.delegate= asyncResponseY;
        return this;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null && !s.equals("")) {
            //Do Something here
            if (delegate != null){
                this.delegate.processFinish(s, requestCode);
                this.delegate.processFinish(s);
            }
            Log.d(TAG, s);

        }
    }
}
