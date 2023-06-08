package com.digilokal.dilo.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.digilokal.dilo.WelcomeActivity
import com.digilokal.dilo.data.UserPreference
import com.digilokal.dilo.data.model.UserModel
import com.digilokal.dilo.databinding.ActivityLoginBinding
import com.digilokal.dilo.helper.ViewModelFactory
import com.digilokal.dilo.ui.custom.PasswordEditText
import com.digilokal.dilo.ui.main.MainActivity
import com.digilokal.dilo.ui.signup.SignupActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity(), PasswordEditText.PasswordValidationListener {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
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

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
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
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Enter your email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Enter your password"
                }
                else -> {
                    loginViewModel.disableButton()
                    loginViewModel.loginUser(email, password)
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
        // Start the WelcomeActivity and clear the activity stack
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }

}