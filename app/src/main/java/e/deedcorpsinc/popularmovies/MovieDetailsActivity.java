package e.deedcorpsinc.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import e.deedcorpsinc.popularmovies.adpater.ViewPagerAdapter;
import e.deedcorpsinc.popularmovies.database.MovieDatabase;
import e.deedcorpsinc.popularmovies.fragment.OverviewFragment;
import e.deedcorpsinc.popularmovies.fragment.ReviewsFragment;
import e.deedcorpsinc.popularmovies.fragment.TrailerFragment;
import e.deedcorpsinc.popularmovies.model.FavouriteMovie;
import e.deedcorpsinc.popularmovies.model.Movie;
import e.deedcorpsinc.popularmovies.model.Review;
import e.deedcorpsinc.popularmovies.model.Video;
import e.deedcorpsinc.popularmovies.utilities.AsyncResponse;
import e.deedcorpsinc.popularmovies.utilities.Constants;
import e.deedcorpsinc.popularmovies.utilities.MovieDBQueryTask;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

import static e.deedcorpsinc.popularmovies.utilities.Constants.FIELD_BACKDROP_PATH;
import static e.deedcorpsinc.popularmovies.utilities.Constants.FIELD_TITLE;
import static e.deedcorpsinc.popularmovies.utilities.Constants.REVIEWS_PATH;
import static e.deedcorpsinc.popularmovies.utilities.Constants.TRAILER_PATH;

public class MovieDetailsActivity extends AppCompatActivity implements AsyncResponse {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    @BindView(R.id.ivDetailPoster)
    ImageView imageView;

    @BindView(R.id.ivBackDrop)
    ImageView ivBackdrop;

//    @BindView(R.id.tvOverview)
//    TextView tvOverview;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tabDetails)
    TabLayout tabLayout;

    @BindView(R.id.viewpagerDetails)
    ViewPager viewPager;

//    @BindView(R.id.tvRating)
//    TextView tvRating;

    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;

    @BindView(R.id.constraintTrailer)
    ConstraintLayout constraintLayout;

    private MovieDatabase mDb;


    Movie movieDetails;

    private static String TRAILER_RESPONSE;
    URL trailerUrl;
    String trailerID;
    Video video;

    //Review object list to be passed to fragment
    List<Review> reviewList = new ArrayList<>();
    URL reviewsURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        mDb = MovieDatabase.getsInstance(getApplicationContext());


        if (getIntent().getExtras().isEmpty()) {
            Toast.makeText(this, "Something went Wrong, No Data Passed!!", Toast.LENGTH_LONG).show();

        } else {
            Bundle detailsBundle = getIntent().getExtras();
            int posterPosition = detailsBundle.getInt("POSITION");
            movieDetails = getMovieDetails(posterPosition);

            //TODO get value from class to prevent wrong poster display
            String poster = detailsBundle.getString("POSTER_URL");
            Picasso.get()
                    .load(poster)
                    .into(imageView);

            String backdrop = movieDetails.getBackdropPath();
            Picasso.get()
                    .load(backdrop)
//                    .error("Could Not load Image")
                    .into(ivBackdrop);

//            tvOverview.setText(movieDetails.getOverview());
//            tvRating.setText(getString(R.string.text_rating, movieDetails.getVote_average()));
            tvReleaseDate.setText(getString(R.string.text_release_date, movieDetails.getReleaseDate()));
            tvTitle.setText(movieDetails.getTitle());
            setTitle(movieDetails.getoriginalTitle());

            //building trailer and reviews URL using moview ID
            trailerUrl = NetworkUtils.buildMyUrl(movieDetails.getMovieId(), TRAILER_PATH);
            reviewsURL = NetworkUtils.buildMyUrl(movieDetails.getMovieId(), REVIEWS_PATH);

            new MovieDBQueryTask(trailerUrl).setListener(this).execute();

        }

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(OverviewFragment.newInstance(movieDetails.getOverview()), "OVERVIEW");
        viewPagerAdapter.addFragment(ReviewsFragment.newInstance(reviewsURL.toString()), "REVIEW");
        //Passing trailer reponse to get details in fragment
        viewPagerAdapter.addFragment(TrailerFragment.newInstance(TRAILER_RESPONSE), "TRAILER");

        viewPager.setAdapter(viewPagerAdapter);

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
                Log.e(TAG, title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Null Json Response");
        }

        return movieDetails;
    }

    //TODO move this method to a different class/folder later
//    public void viewReviews(){
////        FragmentManager fragmentManager= getSupportFragmentManager();
////        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
////        ReviewsFragment reviewsFragment= ReviewsFragment.newInstance(reviewsURL.toString());
////        fragmentTransaction.add(R.id.gridFrameLayout, reviewsFragment);
////        fragmentTransaction.commit();
//
//        Intent reviewsInte= new Intent(this, ReviewActivity.class);
//        reviewsInte.putExtra("REVIEWS", reviewsURL.toString());
//        startActivity(reviewsInte);
//    }

    //Click Handlers [START]
    @OnClick(R.id.fabFavourite)
    void markFavourite() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                FavouriteMovie favouriteMovie;
        String overview = movieDetails.getOverview();
        String rating = movieDetails.getVote_average();
        String releaseDate = movieDetails.getReleaseDate();
        String originalTitle = movieDetails.getoriginalTitle();
        String title = movieDetails.getTitle();
        String backdropPath = movieDetails.getBackdropPath();
        String movieID = movieDetails.getMovieId();
        URL backdropURL = NetworkUtils.buildImageUrl(backdropPath);


                favouriteMovie = new FavouriteMovie(movieID, title, originalTitle, backdropURL.toString(), backdropPath, releaseDate, rating);
                mDb.favouriteDAO().insertAll(favouriteMovie);
            }
        }).start();


    }

//    @OnClick(R.id.tvReviews)
//    void showReviews(){
//        viewReviews();
//    }
    //Click Handlers [END]

    @Override
    public void processFinish(String output, int requestCode) {

    }

    @Override
    public void processFinish(String output) {
        TRAILER_RESPONSE = output;
//        video.setVideos(getVideoDetails(TRAILER_RESPONSE));
        Log.i(TAG, TRAILER_RESPONSE);
    }

    public void playTrailerMethod(String videoID) {
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoID));
        startActivity(youtubeIntent);

    }


}

//Cl