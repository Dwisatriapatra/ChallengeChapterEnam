package com.example.challengechapterenam.networking

import com.example.challengechapterenam.model.GetAllUserResponseItem
import com.example.challengechapterenam.model.PostNewUser
import com.example.challengechapterenam.model.RequestUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiUserServices {
    @GET("datauserlogin")
    fun getAllUser() : Call<List<GetAllUserResponseItem>>
    @POST("datauserlogin")
    fun addDataUser(@Body reqUser : RequestUser) : Call<PostNewUser>
    @PUT("datauserlogin/{id}")
    fun updateDataUser(
        @Path("id") id : String,
        @Body request : RequestUser
    ) : Call<List<GetAllUserResponseItem>>

    companion object{
        var apiUserInterface : ApiUserServices? = null
        fun getInstance() : ApiUserServices{
            if(apiUserInterface == null){
                val baserUrl = "https://6254434c19bc53e2347b93f1.mockapi.io/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(baserUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiUserInterface = retrofit.create(ApiUserServices::class.java)
            }
            return apiUserInterface!!
        }
    }
}