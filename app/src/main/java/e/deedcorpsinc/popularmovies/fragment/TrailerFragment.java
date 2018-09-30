package e.deedcorpsinc.popularmovies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.R;
import e.deedcorpsinc.popularmovies.adpater.TrailerAdapter;
import e.deedcorpsinc.popularmovies.model.Video;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrailerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrailerFragment extends Fragment {

    private static final String ARG_PARAM1 = "TRAILERS";
    @BindView(R.id.trailerRecyclerView)
    RecyclerView recyclerView;

    TrailerAdapter trailerAdapter;

    List<Video> trailerList = new ArrayList<>();


    private String TRAILERS_RESPONSE;
    private String TAG = TrailerFragment.class.getSimpleName();


    public TrailerFragment() {
        // Required empty public constructor
    }


    public static TrailerFragment newInstance(String jsonResponse) {
        TrailerFragment fragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, jsonResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TRAILERS_RESPONSE = getArguments().getString(ARG_PARAM1);
            getVideoDetails(TRAILERS_RESPONSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, view);

        trailerAdapter = new TrailerAdapter(trailerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(trailerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        trailerAdapter.notifyDataSetChanged();

        Log.i(TAG, trailerList.get(0).getName());

        return view;
    }

    private void getVideoDetails(String response) {
        Video video = new Video();

        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray resultsArray = jsonObject.optJSONArray("results");
                //Getting Trailer Objects
                for (int x = 0; x < resultsArray.length(); x++) {
                    JSONObject videoObject = resultsArray.optJSONObject(x);
                    String videoId = videoObject.optString("id");
                    String name = videoObject.optString("name");
                    String site = videoObject.optString("site");
                    String key = videoObject.optString("key");

//                    Log.i(TAG, "VideoName "+name+"\n");
//                    Log.i(TAG, "site "+site+"\n");
//                    Log.i(TAG, "key "+key+"\n");
//                    Log.i(TAG, "VideoId "+videoId+"\n\n");

                    video.setName(name);
                    video.setSite(site);
                    video.setId(videoId);
                    video.setKey(key);

                    trailerList.add(video);
//                videoList.add(video);
                }//end of loop

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        return video;
    }

}
