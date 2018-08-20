package e.deedcorpsinc.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import e.deedcorpsinc.popularmovies.utilities.AsyncResponse;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

import static e.deedcorpsinc.popularmovies.utilities.Constants.POPULAR_MOVIE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.TOP_RATED;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    final static String TAG = MainActivity.class.getSimpleName();

    ImageAdapter imageAdapter;
    private static List<URL> posters = new ArrayList<>();

    @BindView(R.id.gridMovieThumbnails)
    GridView gridView;

    movieDBQueryTask movieDBQueryTask = new movieDBQueryTask();

    URL topRatedURL, mostPopularURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mostPopularURL= NetworkUtils.buildUrl(POPULAR_MOVIE);
        topRatedURL = NetworkUtils.buildUrl(TOP_RATED);

//       Picasso.get()
//               .load(imageUrl.toString())
//               .into(test);


        movieDBQueryTask.execute(topRatedURL);
        movieDBQueryTask.delegate = this;


        checkInternetConnection();

        //Handling poster click: open movie details
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                posters.get(position);
                Intent detailsIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                detailsIntent.putExtra("POSTER_URL", posters.get(position).toString());
                startActivity(detailsIntent);
            }
        });

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

    //inflate sort menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionPopularity) {


            return true;

        } else if (item.getItemId() == R.id.actionTopRated){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //get Async Task result and populate list
    @Override
    public void processFinish(String output) {
        posters = makeImageUrlList(output);
        imageAdapter = new ImageAdapter(this, posters);
        gridView.setAdapter(imageAdapter);

        for (int h = 0; h < posters.size(); h++) {
            Log.e("Posters", posters.get(h).toString() + "\n");
        }

    }
//Async Task class where we downloading movie Details Json
    public class movieDBQueryTask extends AsyncTask<URL, Void, String> {
        public AsyncResponse delegate = null;

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
                delegate.processFinish(s);

            }
        }
    }

    //making image URL from poster paths obtained from JSON response: adding them to a URL list
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
//TODO Sort Images by Popularity or Top Rated score
/**
  Fetch JSON data for both top rated and Popular movies then initialize the list based on selection and pass adapter**/
