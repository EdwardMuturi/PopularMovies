package e.deedcorpsinc.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import e.deedcorpsinc.popularmovies.model.FavouriteMovie;
import e.deedcorpsinc.popularmovies.model.Movie;
import e.deedcorpsinc.popularmovies.utilities.FavouriteDAO;

@Database(entities = {FavouriteMovie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase  extends RoomDatabase {

    private static final String TAG= MovieDatabase.class.getSimpleName();
    private static final Object LOCK= new Object();
    private static final String DATABASE_NAME= "movies";
    private static MovieDatabase sInstance;

    public static MovieDatabase getsInstance(Context context){
        if (sInstance == null){
            Log.e(TAG, "Creating new database instance");
            sInstance= Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        Log.e(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavouriteDAO favouriteDAO();

}
