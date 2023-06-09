package com.digilokal.android.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digilokal.android.data.network.ApiConfig
import com.digilokal.android.data.network.response.MessageItem
import com.digilokal.android.data.repository.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    private val _profile = MutableLiveData<List<MessageItem>>()
    val profile: LiveData<List<MessageItem>> = _profile

    fun getProfile() {
        _isloading.value = true
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val client = ApiConfig.getApiService().getProfile(user.name)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        _isloading.postValue(false)
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _profile.postValue(responseBody.message)
                        }
                    } else {
                            _isloading.postValue(false)

                    }
                } catch (e: IOException) {
                    _isloading.postValue(false)
                }finally {
                        _isloading.postValue(false)
                }
            }
        }
    }
}