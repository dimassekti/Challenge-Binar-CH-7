package com.coufie.challengechapterseven.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coufie.challengechapterseven.model.GetAllUserItem
import com.coufie.challengechapterseven.model.PostUser
import com.coufie.challengechapterseven.network.FilmApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){

    lateinit var userLiveData: MutableLiveData<List<GetAllUserItem>>
    var userUpdateLiveData : MutableLiveData<PostUser> = MutableLiveData()

    init {
        userLiveData = MutableLiveData()
    }

    fun getUserLiveDataObserver() : MutableLiveData<List<GetAllUserItem>>{
        return userLiveData
    }

    fun loginUser(){
        FilmApi.instance.getAllUser()
            .enqueue(object  : retrofit2.Callback<List<GetAllUserItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserItem>>,
                    response: Response<List<GetAllUserItem>>
                ) {
                    if(response.isSuccessful){
                        userLiveData.postValue(response.body())
                    }else{
                        userLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserItem>>, t: Throwable) {
                    userLiveData.postValue(null)
                }


            })
    }

    fun registerUser(username: String,password: String, address: String, image:String, name:String, umur:Int) : Boolean{
        var messageResponse = false
        FilmApi.instance.postUser(
            PostUser(username, password, address, image, name, umur)
        ).enqueue(object : Callback<GetAllUserItem> {
            override fun onResponse(call: Call<GetAllUserItem>, response: Response<GetAllUserItem>) {
                messageResponse = response.isSuccessful
            }

            override fun onFailure(call: Call<GetAllUserItem>, t: Throwable) {
                messageResponse = false
            }

        })
        return messageResponse
    }

    fun updateUser(id :Int, username: String,password: String, name:String, address: String, umur:Int, image:String){
        FilmApi.instance.updateUser(id.toString(), username, password, name, address, umur, image)
            .enqueue(object : Callback<PostUser>{
                override fun onResponse(call: Call<PostUser>, response: Response<PostUser>) {
                    if (response.isSuccessful){
                        userUpdateLiveData.postValue(response.body())
                    }else{
                        userUpdateLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PostUser>, t: Throwable) {
                    userUpdateLiveData.postValue(null)
                }

            })
    }
}