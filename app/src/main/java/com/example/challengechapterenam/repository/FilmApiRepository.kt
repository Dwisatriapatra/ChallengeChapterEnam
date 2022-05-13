package com.example.challengechapterenam.repository

import com.example.challengechapterenam.networking.ApiFilmServices

class FilmApiRepository constructor(private val filmApiFilmServices: ApiFilmServices) {
    fun getAllFilmApi() = filmApiFilmServices.getAllFilm()
}