package e.deedcorpsinc.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.adpater.ReviewsAdapter;
import e.deedcorpsinc.popularmovies.model.Review;
import e.deedcorpsinc.popularmovies.utilities.AsyncResponse;
import e.deedcorpsinc.popularmovies.utilities.MovieDBQueryTask;

public class ReviewActivity extends AppCompatActivity implements AsyncResponse {
    private static final String TAG = ReviewActivity.class.getSimpleName();
    @BindView(R.id.reviewsRecyclerViewA)
    RecyclerView recyclerView;

    List<Review> reviewList = new ArrayList<>();

    String REVIEWS_RESPONSE;

    ReviewsAdapter reviewsAdapter;
    URL reviewsURL;

    String passedURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        if (getIntent().getExtras().containsKey("REVIEWS")) {
            passedURL= getIntent().getStringExtra("REVIEWS");
            try {
                reviewsURL= new URL(passedURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Error in Reviews");
        }

        new MovieDBQueryTask(reviewsURL).setListener(this).execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        reviewsAdapter= new ReviewsAdapter(reviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewsAdapter);
    }

    private void getReview(String response) {
        Review review = new Review();
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray resultsArray = jsonObject.optJSONArray("results");
                //Looping through the array to get all review objects
                for (int x = 0; x < resultsArray.length(); x++) {
                    //Getting Trailer Object, ignoring clip objects
                    JSONObject reviewObject = resultsArray.optJSONObject(x);
                    String author = reviewObject.optString("author");
                    String content = reviewObject.optString("content");

                    review.setAuthor(author);
                    review.setContent(content);

                    reviewList.add(review);
                    Log.e(TAG, content+ "\n");

                }//end of loop

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processFinish(String output, int requestCode) {
    }

    @Override
    public void processFinish(String output) {
        REVIEWS_RESPONSE= output;
        Log.e(TAG, REVIEWS_RESPONSE +"\n");
        getReview(REVIEWS_RESPONSE);
        reviewsAdapter.refreshList(reviewList);

    }
}
