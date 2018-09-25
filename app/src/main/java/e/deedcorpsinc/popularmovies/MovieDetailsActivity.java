package e.deedcorpsinc.popularmovies;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import e.deedcorpsinc.popularmovies.model.Movie;
import e.deedcorpsinc.popularmovies.model.Video;
import e.deedcorpsinc.popularmovies.utilities.AsyncResponse;
import e.deedcorpsinc.popularmovies.utilities.Constants;
import e.deedcorpsinc.popularmovies.utilities.MovieDBQueryTask;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

import static e.deedcorpsinc.popularmovies.utilities.Constants.FIELD_BACKDROP_PATH;
import static e.deedcorpsinc.popularmovies.utilities.Constants.FIELD_TITLE;

public class MovieDetailsActivity extends AppCompatActivity implements AsyncResponse {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    @BindView(R.id.ivDetailPoster)
    ImageView imageView;

    @BindView(R.id.ivBackDrop)
    ImageView ivBackdrop;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvRating)
    TextView tvRating;

    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;

    @BindView(R.id.constraintTrailer)
    ConstraintLayout constraintLayout;

    @BindView(R.id.videoView)
    VideoView videoView;

    Movie movieDetails;

    private static String VIDEO_RESPONSE;
    URL videoUrl;
    String trailerID;
    Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);


        if (getIntent().getExtras().isEmpty()) {
            Toast.makeText(this, "Something went Wrong, No Data Passed!!", Toast.LENGTH_LONG).show();

        } else {
            Bundle detailsBundle = getIntent().getExtras();
            int posterPosition = detailsBundle.getInt("POSITION");
            movieDetails = getMovieDetails(posterPosition);

            String poster = detailsBundle.getString("POSTER_URL");
            Picasso.get()
                    .load(poster)
                    .into(imageView);

            String backdrop = movieDetails.getBackdropPath();
            Picasso.get()
                    .load(backdrop)
//                    .error("Could Not load Image")
                    .into(ivBackdrop);

            tvOverview.setText(movieDetails.getOverview());
            tvRating.setText(getString(R.string.text_rating, movieDetails.getVote_average()));
            tvReleaseDate.setText(getString(R.string.text_release_date, movieDetails.getReleaseDate()));
            tvTitle.setText(movieDetails.getTitle());
            setTitle(movieDetails.getoriginalTitle());

            videoUrl = NetworkUtils.buildVdieoUrl(movieDetails.getMovieId());
            new MovieDBQueryTask(videoUrl).setListener(this).execute();

        }

    }

    private Movie getMovieDetails(int position) {
        Movie movieDetails = new Movie();

        if (Constants.JSON_RESPONSE != null) {
            try {
                JSONObject moviesObject = new JSONObject(Constants.JSON_RESPONSE);
                JSONArray result = moviesObject.optJSONArray("results");

                JSONObject movieDetailsObject = result.optJSONObject(position);
                String overview = movieDetailsObject.optString("overview");
                String rating = movieDetailsObject.optString("vote_average");
                String releaseDate = movieDetailsObject.optString("release_date");
                String originalTitle = movieDetailsObject.optString("original_title");
                String title = movieDetailsObject.optString(FIELD_TITLE);
                String backdropPath = movieDetailsObject.optString(FIELD_BACKDROP_PATH);
                URL backdropURL = NetworkUtils.buildImageUrl(backdropPath);
                String movieId = movieDetailsObject.optString("id");

                movieDetails = new Movie(originalTitle, overview, rating, releaseDate, title, backdropURL.toString(), movieId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Null Json Response");
        }

        return movieDetails;
    }

    //Click Handlers [START]
    @OnClick(R.id.fabPlayTrailer)
    void playTrailer() {
        playTrailerMethod(video.getKey());
        Log.e("TRailer", video.getKey());

    }

    @Override
    public void processFinish(String output, int requestCode) {

    }

    @Override
    public void processFinish(String output) {
        VIDEO_RESPONSE = output;
        video =getVideoDetails(VIDEO_RESPONSE);
        Log.i(TAG, VIDEO_RESPONSE);
    }

    public void playTrailerMethod(String videoID){
        Intent youtubeIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+ videoID));
        startActivity(youtubeIntent);

    }

    private Video getVideoDetails(String response) {
        Video video = new Video();
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray resultsArray = jsonObject.optJSONArray("results");
                //Getting Trailer Object, ignoring clip objects
                JSONObject videoObject = resultsArray.optJSONObject(0);
                String videoId= videoObject.optString("id");
                String name = videoObject.optString("name");
                String site = videoObject.optString("site");
                String key = videoObject.optString("key");

                Log.i(TAG, "VideoName "+name);

                video.setName(name);
                video.setSite(site);
                video.setId(videoId);
                video.setKey(key);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return video;
    }

    //Click Handlers [END]

}
