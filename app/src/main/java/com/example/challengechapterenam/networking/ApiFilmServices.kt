package com.example.challengechapterenam.networking

import com.example.challengechapterenam.model.GetAllFilmResponseItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiFilmServices {
    @GET("film")
    fun getAllFilm(): Call<List<GetAllFilmResponseItem>>

    companion object{
        var apiFilmServices: ApiFilmServices? = null
        fun getInstance() : ApiFilmServices{
            if (apiFilmServices == null) {
                val baseUrl = "https://6254434289f28cf72b5aed04.mockapi.io/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiFilmServices = retrofit.create(ApiFilmServices::class.java)
            }
            return apiFilmServices!!
        }
    }
}