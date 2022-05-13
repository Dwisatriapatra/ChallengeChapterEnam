package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapterenam.model.GetAllUserResponseItem
import com.example.challengechapterenam.model.PostNewUser
import com.example.challengechapterenam.model.RequestUser
import com.example.challengechapterenam.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUserApi(private val repository: UserRepository) : ViewModel() {
    //init live data
    val liveDataUserApi = MutableLiveData<List<GetAllUserResponseItem>>()

//    fun getLiveUserApiObserver() : MutableLiveData<List<GetAllUserResponseItem>>{
//        return liveDataUserApi
//    }
    fun getAllUser(){
        val response = repository.getAllUser()
        response.enqueue(object : Callback<List<GetAllUserResponseItem>>{
            override fun onResponse(
                call: Call<List<GetAllUserResponseItem>>,
                response: Response<List<GetAllUserResponseItem>>
            ) {
                liveDataUserApi.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                //nothing
            }

        })
    }

    fun addNewUser(
        alamat: String,
        email: String,
        image: String,
        username: String,
        tanggalLahir: String,
        password: String,
        name: String
    ) : Boolean{
        var messageResponse = false
        val response = repository.addDataUser(
            RequestUser(alamat, email, image, name, password, tanggalLahir, username)
        )
        response.enqueue(object : Callback<PostNewUser>{
            override fun onResponse(call: Call<PostNewUser>, response: Response<PostNewUser>) {
                messageResponse = response.isSuccessful
            }

            override fun onFailure(call: Call<PostNewUser>, t: Throwable) {
                messageResponse = false
            }
        })
        return messageResponse
    }
    fun updateUser(
        id: String,
        alamat: String,
        email: String,
        image: String,
        username: String,
        tanggalLahir: String,
        password: String,
        name: String
    ) : Boolean{
        var message = false
        val response = repository.updateDataUser(
            id, RequestUser(alamat, email, image, name, password, tanggalLahir, username)
        )
        response.enqueue(object : Callback<List<GetAllUserResponseItem>>{
            override fun onResponse(
                call: Call<List<GetAllUserResponseItem>>,
                response: Response<List<GetAllUserResponseItem>>
            ) {
                message = response.isSuccessful
            }

            override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                message = false
            }

        })
        return message
    }
}