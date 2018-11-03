package e.deedcorpsinc.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//@Entity
public class Movie {

//    @SerializedName("id")
//    @Expose
//    @PrimaryKey(autoGenerate = false)
    private int id;


//    @SerializedName("vote_average")
//    @Expose
    private String voteAverage;

//    @SerializedName("title")
//    @Expose
    private String title;

//    @SerializedName("poster_path")
//    @Expose
    private String posterPath;

//    @SerializedName("original_title")
//    @Expose
    private String originalTitle;


//    @SerializedName("backdrop_path")
//    @Expose
    private String backdropPath;

//    @SerializedName("overview")
//    @Expose
    private String overview;
//    @SerializedName("release_date")
//    @Expose
    private String releaseDate;

    public Movie() {
    }

    public Movie(int id, String voteAverage, String title, String posterPath, String originalTitle, String backdropPath, String overview, String releaseDate) {
        super();
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie(String voteAverage, String title, String posterPath, String originalTitle, String backdropPath, String overview, String releaseDate) {
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    //[START] Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", voteAverage='" + voteAverage + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }


//[END] Getters and Setters

}