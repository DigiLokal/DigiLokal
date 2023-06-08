package com.digilokal.dilo.remote.retrofit

import com.digilokal.dilo.remote.response.AllServicesResponse
import com.digilokal.dilo.remote.response.InfluencersResponse
import com.digilokal.dilo.remote.response.LoginResponse
import com.digilokal.dilo.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/register")
    fun registerUser(@Body requestBody: HashMap<String, Any>): Call<RegisterResponse>


    @Headers("Content-Type: application/json")
    @POST("/login")
    fun loginUser(@Body requestBody: Map<String, String>): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/home/all-services")
    fun searchAllServices(@Body requestBody: Map<String, String>): Call<AllServicesResponse>

    @Headers("Content-Type: application/json")
    @POST("/home/all-influencers")
    fun searchAllInfluencers(@Body requestBody: Map<String, String>): Call<InfluencersResponse>
}
