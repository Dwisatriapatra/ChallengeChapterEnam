package com.example.challengechapterenam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challengechapterenam.repository.UserRepository

class ViewModelFactoryUser constructor(private val repository: UserRepository) :
ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ViewModelUserApi::class.java)){
            ViewModelUserApi(this.repository) as T
        }else{
            throw IllegalArgumentException("View model not found")
        }
    }

}