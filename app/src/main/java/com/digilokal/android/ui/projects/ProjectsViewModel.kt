package com.digilokal.android.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digilokal.android.data.UserPreference
import com.digilokal.android.remote.response.MessageItem
import com.digilokal.android.remote.response.OrderItem
import com.digilokal.android.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ProjectsViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    private val _order = MutableLiveData<List<OrderItem>>()
    val order: LiveData<List<OrderItem>> = _order

    init {
        getOrder()
    }
    private fun getOrder() {
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val client = ApiConfig.getApiService().getOrder(user.name)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        _isloading.postValue(false)
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _order.postValue(responseBody.message)
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