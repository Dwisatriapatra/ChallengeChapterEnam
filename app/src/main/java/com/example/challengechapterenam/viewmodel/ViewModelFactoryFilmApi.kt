package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challengechapterenam.repository.FilmApiRepository

class ViewModelFactoryFilmApi constructor(private val repository: FilmApiRepository) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ViewModelFilmApi::class.java)){
            ViewModelFilmApi(this.repository) as T
        }else{
            throw IllegalArgumentException("View model not found")
        }
    }
}