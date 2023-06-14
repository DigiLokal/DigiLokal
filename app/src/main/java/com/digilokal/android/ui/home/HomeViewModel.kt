package com.digilokal.android.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digilokal.android.data.UserPreference
import com.digilokal.android.remote.response.AllServicesResponse
import com.digilokal.android.remote.response.InfluencerItem
import com.digilokal.android.remote.response.InfluencersResponse
import com.digilokal.android.remote.response.ServicesItem
import com.digilokal.android.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomeViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    private val _influencer = MutableLiveData<List<InfluencerItem>>()
    val influencer: LiveData<List<InfluencerItem>> = _influencer

    private val _services = MutableLiveData<List<ServicesItem>>()
    val services: LiveData<List<ServicesItem>> = _services

    init {
        getInfluencers()
        getServices()
    }


    fun getInfluencers() {
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val client = ApiConfig.getApiService().getRecommendation(user.name)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        val influencerResponse = response.body()
                        if (influencerResponse != null) {
                            _influencer.postValue(influencerResponse.influencer)
                            _isloading.postValue(false)
                        }
                    } else {
                            _isloading.postValue(false)
//                            val errorMessage = detailResponse?.message ?: "Unknown error occurred"
//                            showAlert(errorMessage)

                    }
                } catch (e: IOException) {
//                        showAlert("Something wrong. Please try again.")
                    _isloading.postValue(false)
                } finally {
                        _isloading.postValue(false)
                }
            }
        }
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
                        _isloading.postValue(false)
                    }
                } else {
                    Log.e(TAG, "Error message: ${response.message()}")
                    _isloading.postValue(false)
                }
            }

            override fun onFailure(call: Call<AllServicesResponse>, t: Throwable) {
                Log.e(TAG, "Error message: ${t.message}")
                _isloading.postValue(false)
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}