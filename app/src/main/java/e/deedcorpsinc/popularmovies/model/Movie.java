package e.deedcorpsinc.popularmovies.model;

import java.net.URL;

public class Movie {
    //variable declaration
    private String title;
    private String overview;
    private String vote_average;
    private String releaseDate;

    private URL moviePoster;
    private URL backdropPath;

    //[START] constructor

    public Movie() {

    }

    //constructor without moviePoster field
    public Movie(String originalTitle, String overview, String vote_average, String releaseDate, String title, URL backdropPath) {
        this.overview = overview;
        this.vote_average = vote_average;
        this.releaseDate = releaseDate;
        this.title= title;
        this.backdropPath= backdropPath;
    }
    //[END] Constructor

    // [START] Setters and Gettes
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

    public URL getBackdropPath() {
        return backdropPath;
    }

    //[END] Setter and Getter

}
