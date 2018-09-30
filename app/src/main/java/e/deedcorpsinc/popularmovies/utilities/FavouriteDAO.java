package e.deedcorpsinc.popularmovies.utilities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import e.deedcorpsinc.popularmovies.model.FavouriteMovie;

@Dao
public interface FavouriteDAO {
    @Query("SELECT * FROM FavouriteMovie")
    List<FavouriteMovie> getAll();

    @Insert
    void insertAll(List<FavouriteMovie> favouriteMovies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FavouriteMovie favouriteMovie);

    @Delete
    void delete(FavouriteMovie favouriteMovie);
}
