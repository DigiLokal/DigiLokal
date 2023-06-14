package com.digilokal.android.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.digilokal.android.WelcomeActivity
import com.digilokal.android.data.UserPreference
import com.digilokal.android.data.model.UserModel
import com.digilokal.android.databinding.ActivityLoginBinding
import com.digilokal.android.helper.ViewModelFactory
import com.digilokal.android.helper.setupFullScreenMode
import com.digilokal.android.ui.custom.PasswordEditText
import com.digilokal.android.ui.main.MainActivity
import com.digilokal.android.ui.signup.SignupActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity(), PasswordEditText.PasswordValidationListener {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFullScreenMode(window, supportActionBar)
        setupViewModel()
        setupAction()

        binding.edLoginPassword.setPasswordValidationListener(this)

        loginViewModel.isButtonEnabled.observe(this) { isEnabled ->
            binding.loginButton.isEnabled = isEnabled
            binding.registerButton.isEnabled = isEnabled
        }

        loginViewModel.loginSuccess.observe(this) { success ->
            if (success) {
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("You are logged in.")
                    setPositiveButton("Continue") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }

        loginViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val username = binding.edLoginUsername.text.toString()
            val password = binding.edLoginPassword.text.toString()
            when {
                username.isEmpty() -> {
                    binding.usernameEditTextLayout.error = "Enter your email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Enter your password"
                }
                else -> {
                    loginViewModel.disableButton()
                    loginViewModel.loginUser(username, password)
                }
            }
        }

        binding.registerButton.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onPasswordValidated(isValid: Boolean) {
        if (!isValid) {
            binding.passwordEditTextLayout.error = "Password must be at least $MIN_PASSWORD_LENGTH characters long"
        } else {
            binding.passwordEditTextLayout.error = null
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }

}