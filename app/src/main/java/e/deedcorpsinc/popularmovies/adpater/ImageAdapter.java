package e.deedcorpsinc.popularmovies.adpater;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import e.deedcorpsinc.popularmovies.R;
import e.deedcorpsinc.popularmovies.model.Movie;

public class ImageAdapter extends BaseAdapter {
    private static final String IMAGE_ADAPTER_TAG = ImageAdapter.class.getSimpleName();
    private Context context;
    List<URL> movie;

    public ImageAdapter(Context context, List<URL> movies) {
        this.context = context;
        movie = movies;
    }

    @Override
    public int getCount() {
        if (movie != null)
            return movie.size();
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
        movie.get(position);
        if (convertView == null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }

        ImageView imageView= convertView.findViewById(R.id.movieImage);

        Picasso.get().
                load(movie.get(position).toString())
                .into(imageView);

        return convertView;
    }
}
//TODO CREATE A DETAILS ACTIVITY THAT DISPLAYS CLICKED MOVIE DETAILS
//TODO IMPLEMENT SORT BASED ON POPULARITY OR TOP_RATED CATEGORIES
