package e.deedcorpsinc.popularmovies.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static e.deedcorpsinc.popularmovies.utilities.Constants.MOVIEDB_BASE_URL;

public class RetrofitClientInstance {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            String BASE_URL = "http://api.themoviedb.org/3/";
            retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
