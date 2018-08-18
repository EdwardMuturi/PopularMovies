package e.deedcorpsinc.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.adpater.ImageAdapter;
import e.deedcorpsinc.popularmovies.model.Movie;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

import static e.deedcorpsinc.popularmovies.utilities.Constants.TOP_RATED;
import static e.deedcorpsinc.popularmovies.utilities.NetworkUtils.buildImageUrl;

public class MainActivity extends AppCompatActivity {
    //    @BindView(R.id.test)
//    ImageView test;
    final static String TAG = MainActivity.class.getSimpleName();
    Movie movie = new Movie();

    @BindView(R.id.gridMovieThumbnails)
    GridView gridView;

    String httpsJsonResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        gridView.setAdapter(new ImageAdapter(this));

//        URL imageUrl= buildImageUrl("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        URL topRatedUrl = NetworkUtils.buildUrl(TOP_RATED);

//       Picasso.get()
//               .load(imageUrl.toString())
//               .into(test);

        new movieDBQueryTask().execute(topRatedUrl);

        checkInternetConnection();

    }

    void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected)
            Snackbar.make(gridView, "Active Internet", Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make(gridView, "No Active Internet, Please Connect to Internet to load Images", Snackbar.LENGTH_LONG).show();
    }

    public class movieDBQueryTask extends AsyncTask<URL, Void, String> {
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
            if (s != null && !s.equals("")) {
                //Do Something here
                Log.d(TAG, s);

                List<URL> posters= makeImageUrlList(s);
                for (int h=0; h< posters.size(); h++){
                    Log.d("Posters", posters.get(h).toString() + "\n");
                }
            }
        }
    }

    public List<URL> makeImageUrlList(String moviesJsonResponse) {
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


//COMPLETED Create a model class to load movie
// COMPLETED  CREATE A PArse class to parse the json string
//COMPLETED  CREATE AN ASYNC TASK CLASS TO EXECUTE IN BACKGROUND LOADING THE JSON STRING
