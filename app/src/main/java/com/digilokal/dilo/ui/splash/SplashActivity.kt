package com.digilokal.dilo.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.digilokal.dilo.WelcomeActivity
import com.digilokal.dilo.data.UserPreference
import com.digilokal.dilo.databinding.ActivitySplashBinding
import com.digilokal.dilo.helper.ViewModelFactory
import com.digilokal.dilo.ui.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
    }

    private fun setupViewModel() {
        splashViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[SplashViewModel::class.java]

        splashViewModel.getUser().observe(this) { user ->
            splashViewModel.initSplashScreen()
            val observer = Observer<SplashActivity> {
                if (user.isLogin) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
//
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
                }
            }
            splashViewModel.liveData.observe(this, observer)

        }
    }
}