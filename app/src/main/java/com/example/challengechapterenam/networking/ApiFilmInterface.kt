package com.example.challengechapterenam.networking

import com.example.challengechapterenam.model.GetAllFilmResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiFilmInterface {
    @GET("film")
    fun getAllFilm() : Call<List<GetAllFilmResponseItem>>
}