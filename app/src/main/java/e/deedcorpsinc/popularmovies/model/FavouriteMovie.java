package e.deedcorpsinc.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class FavouriteMovie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieId;
    private String title;
    private String originalTitle;
    private String imageUrl;
    private String backdropUrl;
    private String releaseDate;
    private String Rating;

//    private List<Review> reviews;
//    private List<Video> videos;

    @Ignore
    public FavouriteMovie(String movieId, String title, String originalTitle, String imageUrl, String backdropUrl,
                          String releaseDate, String rating)/** List<Review> reviews, List<Video> videos)**/ {
        this.movieId = movieId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.imageUrl = imageUrl;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        Rating = rating;
//        this.reviews = reviews;
//        this.videos = videos;
    }


    public FavouriteMovie(int id, String movieId, String title, String originalTitle, String imageUrl, String backdropUrl,
                          String releaseDate, String rating)/**List<Review> reviews, List<Video> videos)**/ {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.imageUrl = imageUrl;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        Rating = rating;
//        this.reviews = reviews;
//        this.videos = videos;
    }

    public FavouriteMovie() {
    }

    //Getter and setter [START]
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

//    public List<Review> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(List<Review> reviews) {
//        this.reviews = reviews;
//    }
//
//    public List<Video> getVideos() {
//        return videos;
//    }
//
//    public void setVideos(List<Video> videos) {
//        this.videos = videos;
//    }

//    Getter and Setter [END]
}
