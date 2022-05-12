package com.example.challengechapterenam.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.challengechapterenam.dataclass.FavoriteFilm

@Dao
interface FavoriteFilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm)

    @Query("SELECT * FROM favorite_film")
    fun getFavoriteFilm() : LiveData<List<FavoriteFilm>>

    @Delete
    suspend fun deleteFavoriteFilm(favoriteFilm: FavoriteFilm)

    @Query("DELETE FROM favorite_film WHERE id = :id")
    suspend fun deleteFavoriteFilmById(id: Int)

}