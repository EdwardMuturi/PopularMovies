package e.deedcorpsinc.popularmovies.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.R;
import e.deedcorpsinc.popularmovies.adpater.ReviewsAdapter;
import e.deedcorpsinc.popularmovies.model.Review;
import e.deedcorpsinc.popularmovies.model.Video;
import e.deedcorpsinc.popularmovies.utilities.AsyncResponse;
import e.deedcorpsinc.popularmovies.utilities.Constants;
import e.deedcorpsinc.popularmovies.utilities.MovieDBQueryTask;

public class ReviewsFragment extends Fragment implements AsyncResponse {
    private static final String TAG = "ReviewFragment";
    String userReviews;
    URL reviewsURL;

    String REVIEWS_RESPONSE;

    @BindView(R.id.reviewsRecyclerView)
    RecyclerView recyclerView;

    List<Review> reviewList = new ArrayList<>();

    ReviewsAdapter reviewsAdapter;

    public ReviewsFragment() {
    }

    public static ReviewsFragment newInstance(String review) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_REVIEWS, review);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userReviews = getArguments().getString(Constants.KEY_REVIEWS);
            try {
                reviewsURL = new URL(userReviews);
                new MovieDBQueryTask(reviewsURL).setListener(this).execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Got Review URL");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);

        Log.e(TAG, "AsyncTask Executed\n\n");


        getReviews(REVIEWS_RESPONSE);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsAdapter = new ReviewsAdapter(reviewList);
        recyclerView.setAdapter(reviewsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reviewsAdapter.refreshList(reviewList);
//        Log.i(TAG, reviewList.get(0).getAuthor());


    }

    @Override
    public void processFinish(String output, int requestCode) {

    }

    @Override
    public void processFinish(String output) {
        REVIEWS_RESPONSE = output;
    }

    private void getReviews(String response) {
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
                    Log.e(TAG, author);

                }//end of loop


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
