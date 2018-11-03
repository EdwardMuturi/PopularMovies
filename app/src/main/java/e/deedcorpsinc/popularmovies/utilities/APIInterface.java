package e.deedcorpsinc.popularmovies.utilities;

import e.deedcorpsinc.popularmovies.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetials(@Path("id") int id, @Query("api_key") String apiKey);
}
