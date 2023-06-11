package com.digilokal.dilo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digilokal.dilo.remote.response.AllServicesResponse
import com.digilokal.dilo.remote.response.InfluencersResponse
import com.digilokal.dilo.remote.response.InfluencerItem
import com.digilokal.dilo.remote.response.ServicesItem
import com.digilokal.dilo.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _influencer = MutableLiveData<List<InfluencerItem>>()
    val influencer: LiveData<List<InfluencerItem>> = _influencer

    private val _services = MutableLiveData<List<ServicesItem>>()
    val services: LiveData<List<ServicesItem>> = _services

    init {
        getInfluencer()
        getServices()
    }


    private fun getInfluencer() {

        val client = ApiConfig.getApiService().searchAllInfluencers()
        client.enqueue(object : Callback<InfluencersResponse> {
            override fun onResponse(
                call: Call<InfluencersResponse>,
                response: Response<InfluencersResponse>
            ) {

                if (response.isSuccessful) {
                    val influencerResponse = response.body()
                    if (influencerResponse != null) {
                        _influencer.value = influencerResponse.influencer
//                        showToast("Login successful")
                    }
                } else {
                    Log.e(TAG, "Error message: ${response.message()}")
//                    showToast("Login failed. Please try again.")

                }
            }

            override fun onFailure(call: Call<InfluencersResponse>, t: Throwable) {
                Log.e(TAG, "Error message: ${t.message}")
//                enableButton()
//                _isloading.value = false
//                showToast("Login failed. Please try again.")
            }
        })
    }

    private fun getServices() {

        val client = ApiConfig.getApiService().searchAllServices()
        client.enqueue(object : Callback<AllServicesResponse> {
            override fun onResponse(
                call: Call<AllServicesResponse>,
                response: Response<AllServicesResponse>
            ) {

                if (response.isSuccessful) {
                    val servicesResponse = response.body()
                    if (servicesResponse != null) {
                        _services.value = servicesResponse.services
//                        showToast("Login successful")
                    }
                } else {
                    Log.e(TAG, "Error message: ${response.message()}")
//                    showToast("Login failed. Please try again.")

                }
            }

            override fun onFailure(call: Call<AllServicesResponse>, t: Throwable) {
                Log.e(TAG, "Error message: ${t.message}")
//                enableButton()
//                _isloading.value = false
//                showToast("Login failed. Please try again.")
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}