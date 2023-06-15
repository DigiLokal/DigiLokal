package com.digilokal.android.ui.main.profile.liked

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digilokal.android.data.network.ApiConfig
import com.digilokal.android.data.network.response.InfluencerItem
import com.digilokal.android.data.repository.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class LikedViewModel(private val pref: UserPreference, private val context: Context) : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    private val _influencer = MutableLiveData<List<InfluencerItem>>()
    val influencer: LiveData<List<InfluencerItem>> = _influencer

    init {
        getInfluencers()
    }

    fun getInfluencers() {
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val client = ApiConfig.getApiService().getLikedUser(user.name)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        _isloading.postValue(false)
                        val influencerResponse = response.body()
                        if (influencerResponse != null) {
                            _influencer.postValue(influencerResponse.influencer)
                        }
                    } else {
                        _isloading.postValue(false)
                    }
                } catch (e: IOException) {
                    _isloading.postValue(false)
                } finally {
                    _isloading.postValue(false)
                }
            }
        }
    }

}