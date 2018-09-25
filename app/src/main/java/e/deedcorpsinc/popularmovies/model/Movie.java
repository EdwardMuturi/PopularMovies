package e.deedcorpsinc.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class Movie implements Parcelable{
    //variable declaration
    private String title;
    private String overview;
    private String vote_average;
    private String releaseDate;

    private String moviePoster;
    private String backdropPath;



    private String movieId;

    //[START] constructor

    public Movie() {

    }

    //constructor without moviePoster field
    public Movie(String originalTitle, String overview, String vote_average, String releaseDate, String title, String backdropPath, String mId) {
        this.overview = overview;
        this.vote_average = vote_average;
        this.releaseDate = releaseDate;
        this.title= title;
        this.backdropPath= backdropPath;
        this.movieId= mId;
    }
    //[END] Constructor

    // [START] Setters and Getters
    public String getTitle() {
        return title;
    }

    public String getoriginalTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getMovieId() {
        return movieId;
    }

    //[END] Setter and Getter

    //[START] Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int  flags) {
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(vote_average);
        parcel.writeString(releaseDate);

        parcel.writeString(moviePoster.toString());
        parcel.writeString(backdropPath.toString());

    }

    private Movie (Parcel input){
        title= input.readString();
        overview= input.readString();
        vote_average= input.readString();
        releaseDate= input.readString();
        moviePoster=  input.readString();
        backdropPath= input.readString();
    }

    public  static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
           return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //[END] Parcelable methods


}
