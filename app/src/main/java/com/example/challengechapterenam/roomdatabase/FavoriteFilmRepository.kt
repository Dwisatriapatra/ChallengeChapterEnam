package com.example.challengechapterenam.roomdatabase

import androidx.lifecycle.LiveData
import com.example.challengechapterenam.dataclass.FavoriteFilm

class FavoriteFilmRepository(
    private val favoriteFilmDatabase : FavoriteFilmDatabase
) {
   suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm){
       favoriteFilmDatabase.favoriteFilmDao().insertFavoriteFilm(favoriteFilm)
   }
    fun getFavoriteFilm() : LiveData<List<FavoriteFilm>>{
        return favoriteFilmDatabase.favoriteFilmDao().getFavoriteFilm()
    }
    suspend fun deleteFavoriteFilm(favoriteFilm: FavoriteFilm){
        favoriteFilmDatabase.favoriteFilmDao().deleteFavoriteFilm(favoriteFilm)
    }
    suspend fun deleteFavoriteFilmById(id: Int) = favoriteFilmDatabase.favoriteFilmDao().deleteFavoriteFilmById(id)
}