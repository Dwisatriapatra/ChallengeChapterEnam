package com.example.challengechapterenam.networking

import com.example.challengechapterenam.model.GetAllUserResponseItem
import com.example.challengechapterenam.model.PostNewUser
import com.example.challengechapterenam.model.RequestUser
import retrofit2.Call
import retrofit2.http.*

interface ApiUserInterface {
    @GET("datauserlogin")
    fun getAllUser() : Call<List<GetAllUserResponseItem>>
    @POST("datauserlogin")
    fun postDataUser(@Body reqUser : RequestUser) : Call<PostNewUser>
    @PUT("datauserlogin/{id}")
    fun updateUser(
        @Path("id") id : String,
        @Body request : RequestUser
    ) : Call<List<GetAllUserResponseItem>>
}