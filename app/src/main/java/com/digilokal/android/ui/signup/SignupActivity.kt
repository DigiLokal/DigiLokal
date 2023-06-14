package com.digilokal.android.ui.signup

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
import com.digilokal.android.WelcomeActivity
import com.digilokal.android.data.UserPreference
import com.digilokal.android.databinding.ActivitySignupBinding
import com.digilokal.android.helper.ViewModelFactory
import com.digilokal.android.ui.custom.PasswordEditText
import com.digilokal.android.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignupActivity : AppCompatActivity(), PasswordEditText.PasswordValidationListener {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        setupViewModel()
        setupAction()

        binding.edRegisterPassword.setPasswordValidationListener(this)

        signupViewModel.isButtonEnabled.observe(this) { isEnabled ->
            binding.signupButton.isEnabled = isEnabled
        }

        signupViewModel.registrationSuccess.observe(this) { success ->
            if (success) {
                AlertDialog.Builder(this).apply {
                    setTitle("Yay!")
                    setMessage("Your account is successfully created!")
                    setPositiveButton("Continue") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
        }

        signupViewModel.isLoading.observe(this) {
            showLoading(it)
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
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[SignupViewModel::class.java]
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.usernameEditTextLayout.error = "Enter your username"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Enter your email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Enter your password"
                }

                else -> {
                    signupViewModel.disableButton()
                    signupViewModel.registerUser(name, email, password)
                }
            }
        }
        binding.loginButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
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


    override fun onPasswordValidated(isValid: Boolean) {
        if (!isValid) {
            binding.passwordEditTextLayout.error = "Password must be at least $MIN_PASSWORD_LENGTH characters long"
        } else {
            binding.passwordEditTextLayout.error = null
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}