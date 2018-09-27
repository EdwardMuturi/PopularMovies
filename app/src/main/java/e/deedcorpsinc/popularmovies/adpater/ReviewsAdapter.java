package e.deedcorpsinc.popularmovies.adpater;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.deedcorpsinc.popularmovies.R;
import e.deedcorpsinc.popularmovies.model.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Review> reviewList;

    public ReviewsAdapter(List<Review> reviews) {
        this.reviewList = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_reviews, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review= reviewList.get(position);

        holder.txtReviewAuthor.setText(review.getAuthor());
        holder.txtReviewContent.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ViewHolder  extends RecyclerView.ViewHolder {
        @BindView(R.id.txtReviewContent)
        TextView txtReviewContent;

        @BindView(R.id.txtReviewAuthor)
        TextView txtReviewAuthor;

        ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
