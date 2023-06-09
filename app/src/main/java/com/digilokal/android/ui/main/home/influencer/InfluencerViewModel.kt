package com.digilokal.android.ui.main.home.influencer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digilokal.android.data.network.ApiConfig
import com.digilokal.android.data.network.response.InfluencerItem
import com.digilokal.android.data.network.response.InfluencersResponse
import com.digilokal.android.data.repository.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfluencerViewModel(private val pref: UserPreference) : ViewModel() {

    private val _influencer = MutableLiveData<List<InfluencerItem>>()
    val influencer: LiveData<List<InfluencerItem>> = _influencer

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    init {
        getInfluencer()
    }

    private fun getInfluencer() {
        val client = ApiConfig.getApiService().searchAllInfluencers()
        client.enqueue(object : Callback<InfluencersResponse> {
            override fun onResponse(
                call: Call<InfluencersResponse>,
                response: Response<InfluencersResponse>
            ) {

                if (response.isSuccessful) {
                    _isloading.value = false
                    val influencerResponse = response.body()
                    if (influencerResponse != null) {
                        _influencer.value = influencerResponse.influencer
                    }
                } else {
                    _isloading.value = false
                }
            }

            override fun onFailure(call: Call<InfluencersResponse>, t: Throwable) {
                _isloading.value = false
            }
        })
    }
}