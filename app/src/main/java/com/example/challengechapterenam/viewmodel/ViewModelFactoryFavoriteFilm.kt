package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challengechapterenam.repository.FavoriteFilmRepository

class ViewModelFactoryFavoriteFilm(
    private val repository: FavoriteFilmRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(FavoriteFilmRepository::class.java)
            return constructor.newInstance(repository)
        }catch (e : Exception){

        }
        return super.create(modelClass)
    }
}