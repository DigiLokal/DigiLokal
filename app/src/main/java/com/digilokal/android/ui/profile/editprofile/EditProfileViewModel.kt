package com.digilokal.android.ui.profile.editprofile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digilokal.android.data.UserPreference
import com.digilokal.android.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class EditProfileViewModel(private val pref: UserPreference, private val context: Context) : ViewModel() {

    private val _editSuccess = MutableLiveData<Boolean>()
    val editSuccess: LiveData<Boolean> = _editSuccess

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading


    private val _isButtonEnabled = MutableLiveData<Boolean>(true)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun enableButton() {
        _isButtonEnabled.postValue(true)
    }

    fun disableButton() {
        _isButtonEnabled.postValue(false)
    }

    init {
        _isloading.value = false
    }


    fun editUser(name: String, detail : String) {
        _isloading.value = true
        viewModelScope.launch {
            val user = pref.getUser().first()
            withContext(Dispatchers.IO) {
                try {
                    val requestBody = HashMap<String, String>()
                    requestBody["username"] = user.name
                    requestBody["nama"] = name
                    requestBody["detail"] = detail

                    val client = ApiConfig.getApiService().putProfile(requestBody)
                    val response = client.execute()

                    if (response.isSuccessful) {
                        enableButton()
                        _editSuccess.postValue(true)
                        _isloading.postValue(false)
                        val responseBody = response.body()
                        if (responseBody != null) {
                            showToast(responseBody.message)
                        }

                    } else {
                        _isloading.postValue(false)
                        enableButton()
                        showToast("Unknown Error")

                    }
                } catch (e: IOException) {
                    _isloading.postValue(false)
                    showToast("Something wrong. Please try again.")
                    enableButton()
                }finally {
                    _isloading.postValue(false)
                    enableButton()
                }
            }
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}