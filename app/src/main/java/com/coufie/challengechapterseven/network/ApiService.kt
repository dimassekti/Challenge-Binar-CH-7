package com.coufie.challengechapterseven.network

import com.coufie.challengechapterseven.model.*
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("user")
    fun getAllUser() : Call<List<GetAllUserItem>>

    @GET("film")
    suspend fun getAllFilm() : List<GetAllFilmItem>

    //Login
    @GET("user")
    fun getUser(
        @Query("username") username : String
    ) : Call<List<GetAllUserItem>>

    @POST("user")
    fun postUser(@Body reqUser: PostUser) : Call<GetAllUserItem>

    @PUT("user/{id}")
    @FormUrlEncoded
    fun updateUser(
        @Path("id")id : String,
        @Field("username")username : String,
        @Field("password")password :String,
        @Field("name")name : String,
        @Field("address")adress : String,
        @Field("umur")umur : Int,
        @Field("image")image : String
    ): Call<PostUser>

}