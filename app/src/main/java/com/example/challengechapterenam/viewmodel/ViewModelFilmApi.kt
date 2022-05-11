package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapterenam.model.GetAllFilmResponseItem
import com.example.challengechapterenam.networking.ApiFilmClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFilmApi : ViewModel() {
    private val liveDataFilmApi : MutableLiveData<List<GetAllFilmResponseItem>> = MutableLiveData()
    fun getLiveDataFilmApiObserver() : MutableLiveData<List<GetAllFilmResponseItem>>{
        return liveDataFilmApi
    }
    fun setLiveDataFilmApi(){
        ApiFilmClient.instance.getAllFilm()
            .enqueue(object : Callback<List<GetAllFilmResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllFilmResponseItem>>,
                    response: Response<List<GetAllFilmResponseItem>>
                ) {
                    if(response.isSuccessful){
                        liveDataFilmApi.postValue(response.body())
                    }else{
                        liveDataFilmApi.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetAllFilmResponseItem>>, t: Throwable) {
                    liveDataFilmApi.postValue(null)
                }

            })
    }
}