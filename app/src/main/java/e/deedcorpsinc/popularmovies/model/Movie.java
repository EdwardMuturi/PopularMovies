package e.deedcorpsinc.popularmovies.model;

import java.net.URL;

public class Movie {
    //variable declaration
    String title, overview, vote_average, releaseDate;
    URL moviePoster;

    //[START] constructor

    public Movie() {

    }

    public Movie(URL moviePoster) {
        this.moviePoster= moviePoster;
    }

    public Movie(String title, URL moviePoster, String overview, String vote_average, String releaseDate) {

        this.title = title;
        this.moviePoster = moviePoster;
        this.overview = overview;
        this.vote_average = vote_average;
        this.releaseDate = releaseDate;
    }
    //[END] Constructor

    // [START] Setters and Gettes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(URL moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    //[END] Setter and Getter

}
