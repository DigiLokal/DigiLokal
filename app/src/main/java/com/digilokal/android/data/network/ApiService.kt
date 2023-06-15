package com.digilokal.android.data.network

import com.digilokal.android.data.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/register")
    fun registerUser(@Body requestBody: HashMap<String, Any>): Call<AuthenticationResponse>

    @Headers("Content-Type: application/json")
    @POST("/login")
    fun loginUser(@Body requestBody: Map<String, String>): Call<AuthenticationResponse>

    @Headers("Content-Type: application/json")
    @GET("/home/all-services")
    fun searchAllServices(): Call<AllServicesResponse>

    @Headers("Content-Type: application/json")
    @GET("/home/all-influencers")
    fun searchAllInfluencers(): Call<InfluencersResponse>

    @Headers("Content-Type: application/json")
    @POST("/recommendation/{username}")
    fun getRecommendation(
        @Path("username") username: String
    ): Call<InfluencersResponse>

    @Headers("Content-Type: application/json")
    @GET("/profile/{username}")
    fun getProfile(
        @Path("username") username: String
    ): Call<ProfileResponse>

    @Headers("Content-Type: application/json")
    @GET("/order/{username}")
    fun getOrder(
        @Path("username") username: String
    ): Call<OrderResponse>

    @Headers("Content-Type: application/json")
    @POST("/add-order")
    fun postAddOrder(@Body requestBody: Map<String, String>): Call<MessageResponse>

    @Headers("Content-Type: application/json")
    @PUT("/profile")
    fun putProfile(
        @Body requestBody: Map<String, String>
    ): Call<MessageResponse>

    @Headers("Content-Type: application/json")
    @GET("/influencer/{username}/available-services")
    fun getAvailableServices(
        @Path("username") username: String
    ): Call<AllServicesResponse>

    @Headers("Content-Type: application/json")
    @POST("/like")
    fun postLike(@Body requestBody: Map<String, String>): Call<MessageResponse>

    @Headers("Content-Type: application/json")
    @GET("/like/{username}")
    fun getLikedUser(
        @Path("username") username: String
    ): Call<InfluencersResponse>
}
