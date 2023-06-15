package com.digilokal.android.ui.main.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digilokal.android.data.network.ApiConfig
import com.digilokal.android.data.network.response.AllServicesResponse
import com.digilokal.android.data.network.response.ServicesItem
import com.digilokal.android.data.repository.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class InfluencerDetailViewModel(private val pref: UserPreference, private val context: Context) : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    private val _services = MutableLiveData<List<ServicesItem>>()
    val services: LiveData<List<ServicesItem>> = _services

    private val _rateCardAvailable = MutableLiveData<Boolean>()
    val rateCardAvailable: LiveData<Boolean> = _rateCardAvailable


    init {
        getInfluencerRateCard(influencer_username)
    }

    private fun getInfluencerRateCard(influencer_username: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().getAvailableServices(influencer_username)
        client.enqueue(object : Callback<AllServicesResponse> {
            override fun onResponse(
                call: Call<AllServicesResponse>,
                response: Response<AllServicesResponse>
            ) {
                if (response.isSuccessful) {
                    _isloading.value = false
                    val servicesResponse = response.body()
                    val messageService = response.message()
                    if (servicesResponse != null) {
                        val services = servicesResponse.services
                        _rateCardAvailable.value = messageService != "No data found for the username"
                        _services.value = services
                    }
                } else {
                    _isloading.value = false
//                    showToast(response.message())
                }
            }

            override fun onFailure(call: Call<AllServicesResponse>, t: Throwable) {
                _rateCardAvailable.value = false
                _isloading.value = false
            }
        })
    }

    fun postLike(influencer_username: String) {
        _isloading.value = true
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val requestBody = HashMap<String, String>()
                    requestBody["username"] = user.name
                    requestBody["likes_user"] = influencer_username
                    val client = ApiConfig.getApiService().postLike(requestBody)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _isloading.postValue(false)
                            showToast("Liked!")
                        }
                    } else {
                            _isloading.postValue(false)
                            showToast(response.message())

                    }
                } catch (e: IOException) {
                    showToast("Something wrong. Please try again.")
                    _isloading.postValue(false)

                }finally {
                    _isloading.postValue(false)
                }
            }
        }
    }

    fun postOrder() {
        _isloading.value = true
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val requestBody = HashMap<String, String>()
                    requestBody["username"] = user.name
                    requestBody["service_id"] = service_id
                    val client = ApiConfig.getApiService().postAddOrder(requestBody)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        _isloading.postValue(false)
                        val responseBody = response.body()
                        if (responseBody != null) {
                            showToast("Order has been added.")
                        }
                    } else {
                        _isloading.postValue(false)
                        showToast(response.message())

                    }
                } catch (e: IOException) {
                    _isloading.postValue(false)
                    showToast("Something wrong. Please try again.")
                }finally {
                    _isloading.postValue(false)
                }
            }
        }
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        var influencer_username = "influencer_username"
        var service_id = "service_id"
    }
}
