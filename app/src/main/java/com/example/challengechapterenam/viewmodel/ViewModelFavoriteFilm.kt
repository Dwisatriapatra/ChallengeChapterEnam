package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.challengechapterenam.dataclass.FavoriteFilm
import com.example.challengechapterenam.repository.FavoriteFilmRepository
//view model class for list of favorite film
class ViewModelFavoriteFilm(
    private val repository: FavoriteFilmRepository
) : ViewModel(){
    suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm){
        repository.insertFavoriteFilm(favoriteFilm)
    }
    fun getFavoriteFilm() = repository.getFavoriteFilm()

    suspend fun deleteFavoriteFilmById(id: Int) = repository.deleteFavoriteFilmById(id)
}