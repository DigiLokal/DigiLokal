package com.digilokal.android.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.digilokal.android.WelcomeActivity
import com.digilokal.android.data.UserPreference
import com.digilokal.android.databinding.ActivitySplashBinding
import com.digilokal.android.helper.ViewModelFactory
import com.digilokal.android.ui.main.MainActivity

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
                    val intent = Intent(this, WelcomeActivity::class.java)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            Pair(binding.ivLogo, "logo"),
                        )

                    startActivity(intent, optionsCompat.toBundle())
                    finish()
                }
            }
            splashViewModel.liveData.observe(this, observer)

        }
    }
}