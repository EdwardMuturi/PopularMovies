package e.deedcorpsinc.popularmovies;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ivDetailView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("POSTER_URL")){
        String posterURL= getIntent().getStringExtra("POSTER_URL");
            Picasso.get()
                    .load(posterURL)
                    .into(imageView);
        } else {
            Toast.makeText(this, "Something went Wrong, No Data Passed!!", Toast.LENGTH_LONG).show();
        }
    }
}
