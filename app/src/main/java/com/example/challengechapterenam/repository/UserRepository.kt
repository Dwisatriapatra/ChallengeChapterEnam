package com.example.challengechapterenam.repository

import com.example.challengechapterenam.model.RequestUser
import com.example.challengechapterenam.networking.ApiUserServices
//this repository class will do network call
class UserRepository constructor(private val apiUserServices: ApiUserServices){
    fun getAllUser() = apiUserServices.getAllUser()
    fun addDataUser(reqUser : RequestUser) = apiUserServices.addDataUser(reqUser)
    fun updateDataUser(id : String, reqUser : RequestUser) = apiUserServices.updateDataUser(id, reqUser)
}