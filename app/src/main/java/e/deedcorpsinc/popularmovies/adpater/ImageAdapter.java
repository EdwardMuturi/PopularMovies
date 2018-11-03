package e.deedcorpsinc.popularmovies.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import e.deedcorpsinc.popularmovies.R;
import e.deedcorpsinc.popularmovies.model.Movie;
import e.deedcorpsinc.popularmovies.utilities.NetworkUtils;

public class ImageAdapter extends BaseAdapter {
    private static final String IMAGE_ADAPTER_TAG = ImageAdapter.class.getSimpleName();
    private Context context;
    private List<Movie> movies;

    public ImageAdapter(Context context, List<Movie> movies) {
        this.context = context;
        movies = movies;
    }

    @Override
    public int getCount() {
        if (movies != null)
            return movies.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie= movies.get(position);
        if (convertView == null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView= convertView.findViewById(R.id.movieImage);
        URL imagePath= NetworkUtils.buildImageUrl(movie.getPosterPath());
        Log.e("MainAct", movie.getPosterPath());

        Picasso.get().
                load(imagePath.toString())
                .into(imageView);

        return convertView;
    }
}
//COMPLETED CREATE A DETAILS ACTIVITY THAT DISPLAYS CLICKED MOVIE DETAILS
//TODO IMPLEMENT SORT BASED ON POPULARITY OR TOP_RATED CATEGORIES
