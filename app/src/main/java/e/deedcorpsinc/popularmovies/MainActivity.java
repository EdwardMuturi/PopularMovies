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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.adpater.ImageAdapter;
import e.deedcorpsinc.popularmovies.utilities.AsyncResponse;
import e.deedcorpsinc.popularmovies.utilities.Constants;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;
import static e.deedcorpsinc.popularmovies.utilities.Constants.POPULAR_MOVIE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.TOP_RATED;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ImageAdapter imageAdapter;
    private static List<URL> mostPopularURLList = new ArrayList<>();
    private static List<URL> topRatedURLList = new ArrayList<>();

    @BindView(R.id.gridMovieThumbnails)
    GridView gridView;

    private URL topRatedURL;
    URL mostPopularURL;
    List<String> movieDetails= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mostPopularURL= NetworkUtils.buildUrl(POPULAR_MOVIE);
        topRatedURL = NetworkUtils.buildUrl(TOP_RATED);


//        movieDBQueryTask.execute(topRatedURL);
//        movieDBQueryTask.delegate = this;
        new movieDBQueryTask(topRatedURL).setListener(this, 1).executeOnExecutor(THREAD_POOL_EXECUTOR);
        new movieDBQueryTask(mostPopularURL).setListener(this, 2).executeOnExecutor(THREAD_POOL_EXECUTOR);



        checkInternetConnection();

        //Handling poster click: open movie details
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mostPopularURLList.get(position);
                Bundle detailsBundle= new Bundle();
                detailsBundle.putInt("POSITION", position);
                detailsBundle.putString("POSTER_URL", mostPopularURLList.get(position).toString());
//                detailsBundle.putString("RESPONSE", JSON_RESPONSE);

                Intent detailsIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                detailsIntent.putExtras(detailsBundle);
                startActivity(detailsIntent);
            }
        });

    }

    void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
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
            imageAdapter= new ImageAdapter(this, mostPopularURLList);
            gridView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
            return true;

        } else if (item.getItemId() == R.id.actionTopRated){
            imageAdapter= new ImageAdapter(this, topRatedURLList);
            gridView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //get Async Task result and populate list
    @Override
    public void processFinish(String output, int RC) {
        switch (RC){
            case 1:
                topRatedURLList = makeImageUrlList(output);
                imageAdapter = new ImageAdapter(this, topRatedURLList);
                Constants.JSON_RESPONSE= output;
                gridView.setAdapter(imageAdapter);
                break;
            case 2:
                mostPopularURLList = makeImageUrlList(output);
                imageAdapter = new ImageAdapter(this, mostPopularURLList);
                Constants.JSON_RESPONSE= output;
                gridView.setAdapter(imageAdapter);
                break;
        }
//        mostPopularURLList = makeImageUrlList(output);
//        Constants.JSON_RESPONSE= output;
//        imageAdapter = new ImageAdapter(this, mostPopularURLList);
//        gridView.setAdapter(imageAdapter);
//
//        for (int h = 0; h < mostPopularURLList.size(); h++) {
//            Log.e("Posters", mostPopularURLList.get(h).toString() + "\n");
//        }

    }
//Async Task class where we downloading movie Details Json
    public static class movieDBQueryTask extends AsyncTask<URL, Void, String> {
    private final URL passedURL;
    public AsyncResponse delegate = null;
        int requestCode;

    movieDBQueryTask(URL chosenURL) {
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

        movieDBQueryTask setListener(AsyncResponse asyncResponse, int requestCode){
            this.delegate= asyncResponse;
            this.requestCode= requestCode;
            return this;
        }


        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                //Do Something here
                if (delegate != null){
                    this.delegate.processFinish(s, requestCode);
                }
                Log.d(TAG, s);

            }
        }
    }

    //making image URL from poster paths obtained from JSON response: adding them to a URL list
    private List<URL> makeImageUrlList(String moviesJsonResponse) {
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

