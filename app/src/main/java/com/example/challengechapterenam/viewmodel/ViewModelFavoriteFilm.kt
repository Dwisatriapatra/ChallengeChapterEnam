package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.challengechapterenam.dataclass.FavoriteFilm
import com.example.challengechapterenam.roomdatabase.FavoriteFilmRepository

class ViewModelFavoriteFilm(
    private val repository: FavoriteFilmRepository
) : ViewModel(){
    suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm){
        repository.insertFavoriteFilm(favoriteFilm)
    }
//    suspend fun deleteFavoriteFilm(favoriteFilm: FavoriteFilm){
//        repository.deleteFavoriteFilm(favoriteFilm)
//    }
    fun getFavoriteFilm() = repository.getFavoriteFilm()

    suspend fun deleteFavoriteFilmById(id: Int) = repository.deleteFavoriteFilmById(id)
}