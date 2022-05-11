package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapterenam.model.GetAllUserResponseItem
import com.example.challengechapterenam.model.PostNewUser
import com.example.challengechapterenam.model.RequestUser
import com.example.challengechapterenam.networking.ApiUserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUserApi : ViewModel() {
    //init live data
    var liveDataUserApi: MutableLiveData<List<GetAllUserResponseItem>> = MutableLiveData()

    fun getLiveUserApiObserver() : MutableLiveData<List<GetAllUserResponseItem>>{
        return liveDataUserApi
    }
    fun setLiveDataUserApi(){
        ApiUserClient.instance.getAllUser()
            .enqueue(object : Callback<List<GetAllUserResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    if(response.isSuccessful){
                        liveDataUserApi.postValue(response.body())
                    }else{
                        liveDataUserApi.postValue(null)
                    }
                }
                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    liveDataUserApi.postValue(null)
                }

            })
    }
    fun addNewUserApi(
        alamat: String,
        email: String,
        image: String,
        username: String,
        tanggalLahir: String,
        password: String,
        name: String
    ) : Boolean{
        var messageResponse = false
        ApiUserClient.instance.postDataUser(
            RequestUser(alamat, email, image, name, password, tanggalLahir, username)
        ).enqueue(object : Callback<PostNewUser>{
            override fun onResponse(call: Call<PostNewUser>, response: Response<PostNewUser>) {
                messageResponse = response.isSuccessful
            }

            override fun onFailure(call: Call<PostNewUser>, t: Throwable) {
                messageResponse = false
            }

        })
        return messageResponse
    }
    fun updateUserApi(
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
        ApiUserClient.instance.updateUser(
            id, RequestUser(alamat, email, image, name, password, tanggalLahir, username)
        ).enqueue(object : Callback<List<GetAllUserResponseItem>>{
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