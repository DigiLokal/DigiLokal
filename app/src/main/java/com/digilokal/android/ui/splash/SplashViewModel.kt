package com.digilokal.android.ui.splash

import androidx.lifecycle.*
import com.digilokal.android.data.UserPreference
import com.digilokal.android.data.model.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    var liveData: MutableLiveData<SplashActivity> = MutableLiveData()

    fun initSplashScreen() {
        viewModelScope.launch {
            delay(SPLASH_DELAY_MS)
            updateLiveData()
        }
    }

    private fun updateLiveData() {
        val splashModel = SplashActivity()
        liveData.value = splashModel
    }

    companion object {
        private const val SPLASH_DELAY_MS = 2000L // 2 seconds
    }
}