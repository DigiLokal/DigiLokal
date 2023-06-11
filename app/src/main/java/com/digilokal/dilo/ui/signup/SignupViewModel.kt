package com.digilokal.dilo.ui.signup

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digilokal.dilo.data.UserPreference
import com.digilokal.dilo.remote.response.Login2Response
import com.digilokal.dilo.remote.response.RegisterResponse
import com.digilokal.dilo.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val pref: UserPreference, private val context: Context) : ViewModel() {
    private val _alertMessage = MutableLiveData<String>()
    val alertMessage: LiveData<String> get() = _alertMessage

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    private val _isButtonEnabled = MutableLiveData<Boolean>(true)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun enableButton() {
        _isButtonEnabled.value = true
    }

    fun disableButton() {
        _isButtonEnabled.value = false
    }

    init {
        _isloading.value = false
    }

    fun registerUser(name: String, email: String, password: String) {
        _isloading.value = true
        val requestBody = HashMap<String, Any>()
        requestBody["username"] = name
        requestBody["email"] = email
        requestBody["password"] = password
        requestBody["password_check"] = password

        val client = ApiConfig.getApiService().registerUser(requestBody)
        client.enqueue(object : Callback<Login2Response> {
            override fun onResponse(
                call: Call<Login2Response>,
                response: Response<Login2Response>
            ) {

                _isloading.value = false
                enableButton()
                if (response.isSuccessful) {
                    val storyResponse = response.body()

                    if (storyResponse != null) {
                        _registrationSuccess.value = true
//                        showToast("Registration successful")
                        showToast(storyResponse.message)

//                        showToast(messageResponse)
                    } else {
//                        showToast("Unknown error occurred")
                        showToast("Unknown error occurred")
//                        showToast(messageResponse)
                    }
                } else {
                    val messageResponse = response.message()

                    showToast("Registration failed. Please try again.")
//                    showToast(messageResponse)
                }
            }

            override fun onFailure(call: Call<Login2Response>, t: Throwable) {
                _isloading.value = false
                enableButton()

            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}