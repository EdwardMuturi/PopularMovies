package e.deedcorpsinc.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.model.Movie;
import e.deedcorpsinc.popularmovies.utilities.Constants;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

import static e.deedcorpsinc.popularmovies.utilities.Constants.FIELD_BACKDROP_PATH;
import static e.deedcorpsinc.popularmovies.utilities.Constants.FIELD_TITLE;

public class MovieDetailsActivity extends AppCompatActivity {
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

    Movie movieDetails;

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

            String backdrop = movieDetails.getBackdropPath().toString();
            Picasso.get()
                    .load(backdrop)
//                    .error("Could Not load Image")
                    .into(ivBackdrop);

            tvOverview.setText(movieDetails.getOverview());
            tvRating.setText(getString(R.string.text_rating, movieDetails.getVote_average()));
            tvReleaseDate.setText(getString(R.string.text_release_date, movieDetails.getReleaseDate()));
            tvTitle.setText(movieDetails.getTitle());
            setTitle(movieDetails.getoriginalTitle());
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

                movieDetails = new Movie(originalTitle, overview, rating, releaseDate, title, backdropURL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Null Json Response");
        }

        return movieDetails;
    }

}
