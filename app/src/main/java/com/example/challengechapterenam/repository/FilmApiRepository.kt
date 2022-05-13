package com.example.challengechapterenam.repository

import com.example.challengechapterenam.networking.ApiFilmServices
//this repository class will do network call
class FilmApiRepository constructor(private val filmApiFilmServices: ApiFilmServices) {
    fun getAllFilmApi() = filmApiFilmServices.getAllFilm()
}