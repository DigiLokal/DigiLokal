package com.digilokal.dilo.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.digilokal.dilo.data.UserPreference
import com.digilokal.dilo.data.model.UserModel
import com.digilokal.dilo.remote.response.LoginResponse
import com.digilokal.dilo.remote.retrofit.ApiConfig
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


    fun loginUser(email: String, password : String) {
        _isloading.value = true
        val requestBody = HashMap<String, String>()
        requestBody["username"] = email
        requestBody["password"] = password
        val client = ApiConfig.getApiService().loginUser(requestBody)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                enableButton()
                _isloading.value = false
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val messageResponse = response.message()
                    if (loginResponse != null) {
                        _loginSuccess.value = true
                        showToast("Login successful")
                        showToast(loginResponse.message)

//                        val user = UserModel(loginResponse.name, email, password, loginResponse.loginResult.token, true)
//                        saveUser(user)

                    } else {
//                        showToast("Unknown error occurred")
//                        showToast(loginResponse.message)
                    }
                } else {
                    showToast("Login failed. Please try again.")
                    val messageResponse = response.message()
                    showToast(messageResponse)

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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