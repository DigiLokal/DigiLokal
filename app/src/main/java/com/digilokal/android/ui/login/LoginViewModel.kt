package com.digilokal.android.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.digilokal.android.data.UserPreference
import com.digilokal.android.data.model.UserModel
import com.digilokal.android.remote.response.Login2Response
import com.digilokal.android.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val pref: UserPreference, private val context: Context) : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

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

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
            pref.login()
        }
    }

    init {
        _isloading.value = false
    }


    fun loginUser(username: String, password : String) {
        _isloading.value = true
        val requestBody = HashMap<String, String>()
        requestBody["username"] = username
        requestBody["password"] = password
        val client = ApiConfig.getApiService().loginUser(requestBody)
        client.enqueue(object : Callback<Login2Response> {
            override fun onResponse(
                call: Call<Login2Response>,
                response: Response<Login2Response>
            ) {
                enableButton()
                _isloading.value = false
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        if (loginResponse.message == "Login success!") {
                            _loginSuccess.value = true
                            showToast(loginResponse.message)
                            val user = loginResponse.token?.let {
                                UserModel(username,null, password, it, true)
                            }
                            if (user != null) {
                                saveUser(user)
                            }
                        } else {
                            showToast(loginResponse.message)
                        }

                    } else {
                        showToast("Unknown error occurred")
                    }
                } else {
                    showToast("Login failed. Please try again.")

                }
            }

            override fun onFailure(call: Call<Login2Response>, t: Throwable) {
                enableButton()
                _isloading.value = false
                showToast("Login failed. Please try again.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}