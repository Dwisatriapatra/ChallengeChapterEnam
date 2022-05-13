package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapterenam.model.GetAllFilmResponseItem
import com.example.challengechapterenam.repository.FavoriteFilmRepository
import com.example.challengechapterenam.repository.FilmApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//view model class for list of film
class ViewModelFilmApi(private val repository: FilmApiRepository) : ViewModel() {
    val liveDataFilmApi = MutableLiveData<List<GetAllFilmResponseItem>>()
    //function to get all data of film
    fun getAllFilmApi(){
        val response = repository.getAllFilmApi()
        response.enqueue(object : Callback<List<GetAllFilmResponseItem>>{
            override fun onResponse(
                call: Call<List<GetAllFilmResponseItem>>,
                response: Response<List<GetAllFilmResponseItem>>
            ) {
                liveDataFilmApi.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GetAllFilmResponseItem>>, t: Throwable) {
                //nothing
            }
        })
    }
}