package com.digilokal.android.ui.authentication.signup

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digilokal.android.data.network.ApiConfig
import com.digilokal.android.data.network.response.AuthenticationResponse
import com.digilokal.android.data.repository.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val pref: UserPreference, private val context: Context) : ViewModel() {

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
        client.enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(
                call: Call<AuthenticationResponse>,
                response: Response<AuthenticationResponse>
            ) {

                _isloading.value = false
                enableButton()
                if (response.isSuccessful) {
                    val registerResponse = response.body()

                    if (registerResponse != null) {
                        _registrationSuccess.value = true
                        if (registerResponse.message == "Register success!") {
                            _registrationSuccess.value = true
                            showToast(registerResponse.message)
                        } else {
                            showToast(registerResponse.message)
                        }
                    } else {
                        showToast("Unknown error occurred")
                    }
                } else {
                    showToast("Registration failed. Please try again.")
                }
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                _isloading.value = false
                enableButton()

            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}