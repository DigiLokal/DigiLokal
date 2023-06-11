package com.digilokal.dilo.remote.retrofit

import com.digilokal.dilo.remote.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/register")
    fun registerUser(@Body requestBody: HashMap<String, Any>): Call<Login2Response>


    @Headers("Content-Type: application/json")
    @POST("/login")
    fun loginUser(@Body requestBody: Map<String, String>): Call<Login2Response>

    @Headers("Content-Type: application/json")
    @GET("/home/all-services")
    fun searchAllServices(): Call<AllServicesResponse>

    @Headers("Content-Type: application/json")
    @GET("/home/all-influencers")
    fun searchAllInfluencers(): Call<InfluencersResponse>
}
